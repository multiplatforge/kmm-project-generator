#!/usr/bin/env just --justfile

patch +PATCHES:
    #!/usr/bin/env sh
    set -euxo pipefail

    patch_files=()
    for patch in {{PATCHES}}; do
        patch_files+=(patches/$patch.patch)
    done
    git apply --ignore-space-change --whitespace=fix ${patch_files[@]}


generate APP_NAME='The Foo Bar' SHORT_NAME='Foo Bar' ROOT_PROJECT_NAME='foobar-apps' MODULE_PREFIX='foobar' ORG_IDENTIFIER='app.foobar' APP_WEBSITE='https://foobar.app' OWNER_EMAIL='john.doe@foobar.app':
    #!/usr/bin/env sh
    set -euxo pipefail

    readonly APP_NAME_PASCALCASED={{replace(APP_NAME, ' ', '')}}
    readonly SHORT_NAME_PASCALCASED={{replace(SHORT_NAME, ' ', '')}}
    readonly SHORT_NAME_PACKAGECASED={{replace(lowercase(SHORT_NAME), ' ', '')}}
    readonly ORG_IDENTIFIER_MERGED=$( \
        echo {{ORG_IDENTIFIER}}.$SHORT_NAME_PACKAGECASED \
        | sd --string-mode $SHORT_NAME_PACKAGECASED.$SHORT_NAME_PACKAGECASED $SHORT_NAME_PACKAGECASED
    )
    readonly ORG_PACKAGES_PATH=$(echo $ORG_IDENTIFIER_MERGED | sd '(.+)\.\w+' '$1' | sd --string-mode '.' '/')

    cd my-application/

    # Shared Module
    cd myapp-shared/
    sd \
        'My Application' '{{APP_NAME}}' \
        build.gradle.kts
    sd \
        MyApp $SHORT_NAME_PASCALCASED \
        build.gradle.kts
    sd \
        'https://example.com' '{{APP_WEBSITE}}' \
        build.gradle.kts
    cd ..

    # Android App
    cd myapp-android-app/
    sd \
        'My Application' '{{APP_NAME}}' \
        src/main/res/values/strings.xml
    sd \
        MyApp $SHORT_NAME_PASCALCASED \
        src/main/AndroidManifest.xml \
        src/main/kotlin/orgpackages/myapp/android/MyAppApp.kt \
        src/main/kotlin/orgpackages/myapp/android/MainActivity.kt \
        src/main/kotlin/orgpackages/myapp/android/ui/theme/Theme.kt \
        src/main/res/values/themes.xml

    mv src/main/kotlin/orgpackages/myapp/android/{MyApp,"$SHORT_NAME_PASCALCASED"}App.kt
    cd ..

    # iOS App
    cd myapp-ios-app/
    sd \
        MyApplication $APP_NAME_PASCALCASED \
        MyApplication.xcodeproj/project.pbxproj \
        Podfile
    sd \
        MyApp $SHORT_NAME_PASCALCASED \
        MyApplication.xcodeproj/project.pbxproj \
        MyApplication/MyAppApp.swift \
        MyApplication/ContentView.swift
    sd \
        orgIdentifier {{ORG_IDENTIFIER}} \
        MyApplication.xcodeproj/project.pbxproj

    mv MyApplication/{MyApp,"$SHORT_NAME_PASCALCASED"}App.swift
    mv {MyApplication,$APP_NAME_PASCALCASED}.xcodeproj
    mv MyApplication $APP_NAME_PASCALCASED
    cd ..

    # Packages
    sd orgpackages.myapp $ORG_IDENTIFIER_MERGED properties.gradle.kts
    fd \
        --type file \
        --extension kt \
        --exec sd orgpackages.myapp $ORG_IDENTIFIER_MERGED {}
    fd \
        --type directory \
        --case-sensitive \
        --glob myapp \
        --exec mv {} {//}/$SHORT_NAME_PACKAGECASED
    fd \
        --type directory \
        --case-sensitive \
        --glob orgpackages \
        --exec sh -c "
            mkdir -p {//}/$ORG_PACKAGES_PATH \
            && mv {}/* {//}/$ORG_PACKAGES_PATH \
            && rmdir {} \
        "

    # Module Prefixes
    sd \
        myapp {{MODULE_PREFIX}} \
        settings.gradle.kts \
        properties.gradle.kts \
        myapp-shared/build.gradle.kts \
        myapp-android-app/build.gradle.kts \
        myapp-ios-app/Podfile \
        .idea/scopes/*

    mv {myapp,{{MODULE_PREFIX}}}-shared/
    mv {myapp,{{MODULE_PREFIX}}}-android-app/
    mv {myapp,{{MODULE_PREFIX}}}-ios-app/

    # Root Project
    sd \
        john.doe@foobar.app {{OWNER_EMAIL}} \
        CODEOWNERS
    sd \
        my-application {{ROOT_PROJECT_NAME}} \
        settings.gradle.kts \
        .idea/scopes/*
    cd ..
    mv my-application {{ROOT_PROJECT_NAME}}