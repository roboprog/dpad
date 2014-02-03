default: build

# note that install will prompt to run, anyway
all: clean build install # run

# ======= macros ======= 

# directory for the Java files
APP_SRC_PKG=src/com/roboprogs/dpad

# directory for the class files
APP_CLS_PKG=build/classes/com/roboprogs/dpad

# sample class file (all built at same time)
SAMPLE_CLASS=$(APP_CLS_PKG)/MainActivity.class

# ======= targets ======= 

clean:
	rm -rf build/classes/*
	rm -f build/*.*  # avoid carping about subdir
	rm -rf dist/*
	rm -f $(APP_SRC_PKG)/R.java

build: dist/dpad_signed.apk

# mk_bld_dirs:
#	mkdir -m 770 -p dist
#	mkdir -m 770 -p build/classes

$(APP_SRC_PKG)/R.java : AndroidManifest.xml \
		res/layout/main.xml \
		res/values/strings.xml
	aapt p \
		-f \
		-v \
		-M AndroidManifest.xml \
		-F ./build/resources.res \
		-I ~/system/classes/android.jar \
		-S res/ \
		-J $(APP_SRC_PKG)

$(SAMPLE_CLASS) : $(APP_SRC_PKG)/*.java \
		$(APP_SRC_PKG)/R.java
	mkdir -p build/classes
	( cd src ; \
	javac -verbose \
		-cp ../libs/demolib.jar \
		-d ../build/classes \
		com/roboprogs/dpad/*.java )

build/dpad.dex : $(SAMPLE_CLASS)
	# jvm/class to dex ('droid executable) conversion
	( cd build/classes ; \
	dx --dex \
		--verbose \
		--no-strict \
		--output=../dpad.dex \
		com \
		../../libs/demolib.jar )

dist/dpad.apk : build/dpad.dex \
		build/resources.res \
		$(APP_SRC_PKG)/R.java
	# android packager
	apkbuilder \
		dist/dpad.apk \
		-v \
		-u \
		-z build/resources.res \
		-f build/dpad.dex 

dist/dpad_signed.apk : dist/dpad.apk
	( cd dist ; \
	signer dpad.apk dpad_signed.apk )

install: build
	# rm /sdcard/dpad_signed.apk
	# put self signed android package on "SD Card"
	cp -f ./dist/dpad_signed.apk /sdcard/
	# use activity manager to install/update app
	am start \
		--user 0 \
		-a android.intent.action.VIEW \
		-t application/vnd.android.package-archive \
		-d file:///sdcard/dpad_signed.apk

run:
	# use activity manager to start app
	am start \
		--user 0 \
		-a android.intent.action.MAIN \
		-n com.roboprogs.dpad/.MainActivity


# vim: ts=4 sw=4 ai
# *** EOF ***
