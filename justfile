#!/usr/bin/env just --justfile

generate APP_NAME='The Foo Bar' MODULE_PREFIX='foobar' ORG_NAME='Example, Inc.' ORG_IDENTIFIER='org.example':
    #!/usr/bin/env sh
    set -euo pipefail

    readonly APP_NAME_PASCALCASED={{replace(APP_NAME, ' ', '')}}
    readonly APP_NAME_PACKAGECASED={{replace(lowercase(APP_NAME), ' ', '')}}
    readonly APP_NAME_KEBABCASED={{replace(lowercase(APP_NAME), ' ', '-')}}
    readonly ORG_IDENTIFIER_PATH={{replace(ORG_IDENTIFIER, '.', '/')}}

    # Packages
    cd my-application/
    fd \
        --type file \
        --extension kt --extension kts --extension xml \
        --exec sh -c "
            sd myapplication $APP_NAME_PACKAGECASED '{}' \
            && sd orgpackages {{ORG_IDENTIFIER}} '{}' \
        "
    fd \
        --type directory \
        --case-sensitive \
        --glob myapplication \
        --exec mv {} {//}/$APP_NAME_PACKAGECASED
    fd \
        --type directory \
        --case-sensitive \
        --glob orgpackages \
        --exec sh -c "
            mkdir -p {//}/$ORG_IDENTIFIER_PATH \
            && mv {}/* {//}/$ORG_IDENTIFIER_PATH \
            && rmdir {} \
        "

    # iOS Project
    cd myapp-ios-app/
    sd \
        MyApplication $APP_NAME_PASCALCASED \
        MyApplication.xcodeproj/project.pbxproj \
        Podfile
    sd \
        orgIdentifier {{ORG_IDENTIFIER}} \
        MyApplication.xcodeproj/project.pbxproj
    sd \
        orgName '"{{ORG_NAME}}"' \
        MyApplication.xcodeproj/project.pbxproj

    mv MyApplication $APP_NAME_PASCALCASED
    mv {MyApplication,$APP_NAME_PASCALCASED}.xcodeproj
    cd ..

    # Module Prefix
    sd \
        myapp {{MODULE_PREFIX}} \
        myapp-ios-app/Podfile \
        myapp-shared/build.gradle.kts \
        myapp-android-app/build.gradle.kts \
        settings.gradle.kts

    mv {myapp,{{MODULE_PREFIX}}}-shared/
    mv {myapp,{{MODULE_PREFIX}}}-android-app/
    mv {myapp,{{MODULE_PREFIX}}}-ios-app/

    # Root Project
    sd \
        my-application $APP_NAME_KEBABCASED \
        settings.gradle.kts
    cd ..
    mv my-application $APP_NAME_KEBABCASED