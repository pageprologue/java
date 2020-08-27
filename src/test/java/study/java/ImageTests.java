package study.java;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import study.java.io.ImageIOStudy;

@SpringBootTest
class ImageTests {

	@Test
	void readImageToByteTests() {
		String fileName = "D:/DATA/img/earth.png";
		ImageIOStudy imageIOStudy = new ImageIOStudy();

		imageIOStudy.readImageToByte(fileName);
		
	}

}
