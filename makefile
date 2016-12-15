default: build

.PHONY: build

clean:
	gradle clean
	rm -rf build/intermediates *.zip

build:
	gradle build

install:
	gradle installDebug

test:
	gradle build installDebug

pkg: package
package:
	zip -r Project_KalevKaldaSikes_100425828.zip \
		app build gen gradle tool \
		doortap.iml build.gradle gradlew gradlew.bat \
		local.properties makefile settings.gradle
