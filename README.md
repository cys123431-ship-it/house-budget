# House Budget

안드로이드 스마트폰에서 바로 동작하는 간단한 가계부 앱입니다.  
Compose + Room 기반으로 수입/지출 내역을 입력하고 월별 내역/합계를 확인할 수 있습니다.

## 실행 화면 핵심
- 이번 달 월 단위 이동
- 수입/지출/잔액 요약 카드
- 거래 추가/삭제
- 간단한 카테고리 태그

## 로컬 실행 방법
1. Android Studio에서 프로젝트 열기
2. `app` 모듈에서 `Run` 또는 `Run > Run 'app'`

## GitHub 업로드 & 릴리즈 (요청한 흐름)

요청하신 대로 `cys123431-ship-it` 계정에 올리려면 아래 순서로 진행합니다.

### 1) GitHub 리포지터리 생성
- GitHub 웹에서 새 리포지터리 생성
  - 예: `house-budget`

### 2) 로컬 커밋/푸시
```bash
git init
git add .
git commit -m "초기 가계부 앱 구현"
git branch -M main
git remote add origin https://github.com/cys123431-ship-it/house-budget.git
git push -u origin main
```

### 3) APK 릴리스
릴리즈는 GitHub Actions로 자동 빌드 + 업로드가 설정되어 있습니다.
태그를 만들면 릴리즈가 자동 생성됩니다.

```bash
git tag v0.1.0
git push origin v0.1.0
```

태그를 푸시하면 `.github/workflows/release-apk.yml`가 실행되어 `app-release.apk`를 Release 자산으로 업로드합니다.

## API 키/패스워드/토큰
이 프로젝트는 별도의 API 키가 필요 없습니다.
