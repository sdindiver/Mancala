package com.game;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.game.KalahApp;

@RunWith(SpringRunner.class)
public class ApplicationStartTest {
  @Test
  public void applicationStarts() {
    KalahApp.main(new String[] {});
  }
}