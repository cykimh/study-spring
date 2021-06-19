package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService {

    // private final MemberRepository memberRepository = new MemoryMemberRepository();

    // 할인 정책을 변경하려면 이 코드를 변경해야하는데....
    // 역할 구현 충실 분리 -> OK
    // 다형성도 활용, 인터페이스 구현 객체를 분리 -> OK
    // OCP, DIP 같은 객체지향 설계원칙을 충실히 준수했다 -> 그렇게 보이지만 사실 아니다...
    // 추상에도 의존하지만, 실제 구체 클래스 FixDiscountPolicy, RateDiscountPolicy도 의존하고있다
    // -> DIP(의존관계 역전 원칙) 위반이랍니다.

    // private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
    // private final DiscountPolicy discountPolicy = new RateDiscountPolicy();

    // 다음과 같이 주석을 변경하는 순간 OrderServiceImpl 소스도 함께 변경해야한다??
    // -> OCP(개방-폐쇠 원칙)위반

    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
