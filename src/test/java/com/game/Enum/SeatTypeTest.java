package com.game.Enum;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.game.Enum.Seat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SeatTypeTest {

	
	@Test
    public void checkSeatType() {
		Assert.assertEquals(Seat.getSeatType(1),Seat.NORTH);
		Assert.assertEquals(Seat.getSeatType(12),Seat.SOUTH);

    }
	
}
