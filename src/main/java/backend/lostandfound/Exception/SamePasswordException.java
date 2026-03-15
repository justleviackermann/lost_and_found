package backend.lostandfound.Exception;

public class SamePasswordException extends RuntimeException{
    public SamePasswordException(String message){
        super(message);
    }
}
