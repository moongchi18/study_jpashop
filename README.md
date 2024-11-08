# jpa 1
## 변경감지 vs 병합
### 변경감지
    @Transactional 대상 메서드에 영속성 엔티티를 반환시키면 변경된 부분만 업데이트
### 병합
    준영속엔티티 값으로 영속성 엔티티 만듦
    영속성 엔티티를 인자값 데이터로 업데이트(빈 값인 경우 null로 업데이트 되어 권장되지 않음)

# jpa 2
## NToN Entity를 출력하려고 하는 경우 양방향 참조로 인하여 무한루프 발생
## Lazy 로딩인 경우 필요하지 않은 객체는 프록새객체로 생성되는데, ObjectMapper에서 객체 -> String 변환 시 프록시 객체인 경우 에러 발생
    -> Hibernate5Module 필요

implementation 'com.fasterxml.jackson.datatype:jackson-datatype-hibernate5-jakarta'

> 엔티티를 출력할 때는 양방향 연관관계가 걸린 곳은 꼭! 한곳을 `@JsonIgnore` 처리 필요(그렇지 않으면 무한루프 발생)
`Hibernate5Module`는 권장되지 않음(엔티티 직접 노출보다는 DTO 활용)
지연 로딩(LAZY)을 피하기 위해 즉시 로딩(EARGR)으로 설정하면 
필요 없는 경우에도 데이터를 항상 조회해서 성능 문제가 발생하여 성능 튜닝이 어려워 진다.
항상 지연 로딩을 기본으로 하고, 성능 최적화가 필요한 경우에는 페치 조인(fetch join)을 사용

> LAZY: LAZY 대상 필드가 사용되기 전에 조회를 하지 않음 -> 
> 2개의 주문이 있는 경우 처음 Order를 조회하고 각 Order마다 2회(Member, Delivery) 조회 
쿼리가 총 1 + N + N번 실행된다. (v1과 쿼리수 결과는 같다.)
`order` 조회 1번(order 조회 결과 수가 N이 된다.)
`order -> member` 지연 로딩 조회 N 번
`order -> delivery` 지연 로딩 조회 N 번
예) order의 결과가 4개면 최악의 경우 1 + 4 + 4번 실행된다.(최악의 경우)
단, 이미 조회된 경우 쿼리를 생략한다.(지연로딩은 영속성 컨텍스트에서 조회하므로) 