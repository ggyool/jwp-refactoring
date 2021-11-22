# 키친포스

## 요구 사항

### Product
- (이름, 가격)으로 상품을 추가한다. (create)
  - 가격은 null 이거나 음수일 수 없다.
- 전체 상품의 리스트를 가져온다. (list)

### MenuGroup
- (이름)으로 메뉴 그룹을 추가한다. (create)
- 전체 메뉴 그룹의 리스트를 가져온다. (list)

### Menu
- (이름, 가격, 메뉴 그룹 아이디, 복수의 메뉴 상품)으로 메뉴를 추가한다. (create)
  - 가격은 null 이거나 음수일 수 없다.
  - 추가할 메뉴의 메뉴 그룹 아이디는 존재해야 한다.
  - 메뉴 상품 리스트가 가지고 있는 상품은 모두 존재해야 한다.
  - 메뉴 상품 리스트의 `∑(상품가격 * 수량)`이 추가할 메뉴의 가격보다 작거나 같아야 한다.
- 전체 메뉴의 리스트를 가져온다. (list)

### Order
- (주문 테이블 아이디, 주문 항목 리스트)으로 주문을 추가한다. (create)
  - 주문 항목은 하나라도 존재해야 한다.
  - 주문 항목 리스트의 모든 메뉴는 존재해야 한다.
  - 주문 항목 리스트의 메뉴는 중복이 있으면 안 된다.
  - 주문 테이블은 존재해야 한다.
  - 주문 테이블이 빈 테이블이면 안 된다.
- 전체 주문의 리스트를 가져온다. (list)
- (주문 아이디, 주문 상태)로 주문 상태를 변경한다. (changeOrderStatus)
  - 주문 아이디를 가지는 주문은 존재해야 한다.
  - 수정 전 주문 상태는 `완료`일 수 없다.
  
### Table
- (방문한 손님 수, 비어 있음 여부)로 테이블을 추가한다. (create)
  - 방문한 손님 수는 필수가 아니다.
- 전체 테이블의 리스트를 가져온다. (list)
- (테이블 아이디, 변경할 비어 있음 여부)로 테이블의 비어 있음 여부를 변경한다. (changeEmpty)
  - 테이블은 존재해야 한다.
  - 테이블이 단체 지정되어 있으면 변경할 수 없다.
  - 테이블에 조리 또는 식사 상태의 주문이 있으면 변경할 수 없다.
- (테이블 아이디, 변경할 손님 수)로 테이블의 방문한 손님 수를 변경한다. (changeNumberOfGuests)
  - 변경할 손님 수가 음수일 수 없다.
  - 테이블은 존재해야 한다.
  - 빈 테이블이면 변경할 수 없다.

## 용어 사전
| 한글명 | 영문명 | 설명 |
| --- | --- | --- |
| 상품 | product | 메뉴를 관리하는 기준이 되는 데이터 |
| 메뉴 그룹 | menu group | 메뉴 묶음, 분류 |
| 메뉴 | menu | 메뉴 그룹에 속하는 실제 주문 가능 단위 |
| 메뉴 상품 | menu product | 메뉴에 속하는 수량이 있는 상품 |
| 금액 | amount | 가격 * 수량 |
| 주문 테이블 | order table | 매장에서 주문이 발생하는 영역 |
| 빈 테이블 | empty table | 주문을 등록할 수 없는 주문 테이블 |
| 주문 | order | 매장에서 발생하는 주문 |
| 주문 상태 | order status | 주문은 조리 ➜ 식사 ➜ 계산 완료 순서로 진행된다. |
| 방문한 손님 수 | number of guests | 필수 사항은 아니며 주문은 0명으로 등록할 수 있다. |
| 단체 지정 | table group | 통합 계산을 위해 개별 주문 테이블을 그룹화하는 기능 |
| 주문 항목 | order line item | 주문에 속하는 수량이 있는 메뉴 |
| 매장 식사 | eat in | 포장하지 않고 매장에서 식사하는 것 |
