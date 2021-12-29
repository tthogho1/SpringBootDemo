package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 　@SpringBootApplicationで
 * 　　Spring の色々な設定を Java コード上で行えます
 *  　依存関係を追加するだけで Spring MVC などのライブラリを設定記述なしで使えるようになる
 *　　　@Component でアノテートされたクラスを Bean としてコンテナに登録します
 */
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
