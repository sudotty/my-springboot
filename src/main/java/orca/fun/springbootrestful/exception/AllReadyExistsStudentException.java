package orca.fun.springbootrestful.exception;

/**
 * @author orca
 * @date 2018/6/14
 */
public class AllReadyExistsStudentException extends RuntimeException {
    private final int id;

    public AllReadyExistsStudentException(int id) {
        this(id, "Could not find student id " + id);
    }

    public AllReadyExistsStudentException(int id, String message) {
        super(message);
        this.id = id;
    }

    /**
     * @return the name of the stream definition that could not be found
     */
    public int getName() {
        return id;
    }

}
