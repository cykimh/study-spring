package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.*;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 구성정보
@Configuration
public class AppConfig {

    @Bean
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    // 역할이 다 들어난다
    // 이 코드만 바꾸면 된다
    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    // 역할이 들어난다
    // @Bean(name = "discountPolicy2") // 네이밍을 지정할 수 있다.
    @Bean
    public DiscountPolicy discountPolicy() {
//         return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }
}
