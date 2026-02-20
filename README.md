# House Budget

Kotlin + Jetpack Compose 기반 가계부 Android 앱입니다.  
수입/지출 내역을 입력하고 월별로 요약해서 보여줍니다.

## 실행

1. Android Studio에서 프로젝트 열기
2. `app` 모듈 선택 후 실행 (`Run`)

## GitHub 업로드 및 APK 릴리즈

현재 로컬은 `main` 브랜치에 커밋되어 있으며, `v0.1.0` 태그도 붙어 있습니다.

### 1. GitHub 리포지터리 먼저 생성
`https://github.com/cys123431-ship-it` 계정에서 `house-budget` 이름으로 새 리포지터리 생성

### 2. 리포지터리 연결 + 푸시
```bash
git remote add origin https://github.com/cys123431-ship-it/house-budget.git
git push -u origin main
```

### 3. APK 릴리즈 태그 푸시
```bash
git tag v0.1.0
git push origin v0.1.0
```

`.github/workflows/release-apk.yml`가 GitHub에서 tag 푸시에 따라 `gradle assembleRelease`를 실행하고
APK를 Release asset으로 업로드합니다.

## 참고
- Java/Android Studio가 없는 환경에서는 빌드가 불가합니다.
- GitHub 릴리즈 자산은 `app/build/outputs/apk/release/app-release.apk` 입니다.
