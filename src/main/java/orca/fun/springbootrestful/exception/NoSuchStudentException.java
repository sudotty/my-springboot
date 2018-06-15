package orca.fun.springbootrestful.exception;

/**
 * @author orca
 * @date 2018/6/14
 */
public class NoSuchStudentException extends RuntimeException {
    private final int id;

    public NoSuchStudentException(int id) {
        this(id, "Could not find student id  " + id);
    }

    public NoSuchStudentException(int id, String message) {
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
