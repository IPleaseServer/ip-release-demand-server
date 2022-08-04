## ip-release-demand-server

- IP할당해제신청 추가 (POST /api/v1/demand/release)
- IP할당해제신청 취소 (DELETE /api/v1/demand/release)
    - 신청을 즉시 삭제한다.
- IP할당해제신청 수락 (PUT /api/v1/demand/release/{demandId}/status/accept)
    - 신청을 삭제대기열에 추가한다.
    - IP할당해제신청수락 메세지를 발행한다.
- 삭제대기열 내 신청 제거
- IP할당해제예약 추가
- IP할당해제예약 취소
- IP할당해제예약 수행
- 할당IP추가시 IP할당해제예약 추가