package DB.entities;

public enum Role {
   SALESMAN, BUYER;

   public String getName() {
      return name().toLowerCase();
   }
}
