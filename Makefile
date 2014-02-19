default: build

# note that install will prompt to run, anyway
all: clean build install # run

# ======= macros ======= 

# base name to use for app identifiers
BNAME=dpad

# directory for the Java files
APP_SRC_PKG=src/com/roboprogs/$(BNAME)

# directory for the class files
APP_CLS_PKG=build/classes/com/roboprogs/$(BNAME)

# Java package name of app's main class
APP_J_PKG=com.roboprogs.$(BNAME)

# sample class file (all built at same time)
SAMPLE_CLASS=$(APP_CLS_PKG)/MainActivity.class

# ======= targets ======= 

tags: $(APP_SRC_PKG)/*.java
	ctags $(APP_SRC_PKG)/*.java

clean:
	rm -f tags
	rm -rf build/classes/*
	rm -f build/*.*  # avoid carping about subdir
	rm -rf dist/*
	rm -f $(APP_SRC_PKG)/R.java

build: dist/$(BNAME)_signed.apk

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
	mkdir -m 770 -p build/classes
	( cd src ; \
	javac -verbose \
		-cp ../libs/demolib.jar \
		-d ../build/classes \
		com/roboprogs/$(BNAME)/*.java )

build/$(BNAME).dex : $(SAMPLE_CLASS)
	# jvm/class to dex ('droid executable) conversion
	( cd build/classes ; \
	dx --dex \
		--verbose \
		--no-strict \
		--output=../$(BNAME).dex \
		com \
		../../libs/demolib.jar )

dist/$(BNAME).apk : build/$(BNAME).dex \
		build/resources.res \
		$(APP_SRC_PKG)/R.java
	mkdir -m 770 -p dist
	# android packager
	apkbuilder \
		dist/$(BNAME).apk \
		-v \
		-u \
		-z build/resources.res \
		-f build/$(BNAME).dex 

dist/$(BNAME)_signed.apk : dist/$(BNAME).apk
	( cd dist ; \
	signer $(BNAME).apk $(BNAME)_signed.apk )

install: build
	# rm /sdcard/$(BNAME)_signed.apk
	# put self signed android package on "SD Card"
	cp -f ./dist/$(BNAME)_signed.apk /sdcard/
	# use activity manager to install/update app
	am start \
		--user 0 \
		-a android.intent.action.VIEW \
		-t application/vnd.android.package-archive \
		-d file:///sdcard/$(BNAME)_signed.apk

run:
	# use activity manager to start app
	am start \
		--user 0 \
		-a android.intent.action.MAIN \
		-n $(APP_J_PKG)/.MainActivity


# vim: ts=4 sw=4 ai
# *** EOF ***
