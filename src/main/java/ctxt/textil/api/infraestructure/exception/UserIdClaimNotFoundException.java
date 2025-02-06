package ctxt.textil.api.infraestructure.exception;

public class UserIdClaimNotFoundException extends RuntimeException{
    public UserIdClaimNotFoundException(){
        super("User Id claim not found");
    }
    public UserIdClaimNotFoundException(Long id){
        super("Claim with id "+id+" not found");
    }

}
