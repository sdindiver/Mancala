package com.game.exception;
public enum ErrorCode implements ApplicationCode {
   TURN_CHANGED_ALREADY("400", "Wrong player turn is now allowed"),
   WRONG_PIT_MOVE_NOT_ALLOWED("400", "Pit is Kalah. Move is not allowed"),
   ZERO_STONE_PIT_MOVE_NOT_ALLOWED("400", "Pit is Kalah. Move is not allowed"),
   PIT_ACCESS_NOT_AUTHROIZED("400", "This is not current turn Player's pits.");


  private String code;
  private String message;

  ErrorCode(String code, String message) {
       this.code = code;
       this.message = message;
   }

   @Override
   public String code() {
       return code;
   }

   @Override
   public String message() {
       return message;
   }

}