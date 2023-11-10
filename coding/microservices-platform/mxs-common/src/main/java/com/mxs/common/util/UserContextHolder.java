package  com.mxs.common.util;
public class UserContextHolder {
    public static ThreadLocal<UserContext> context = new InheritableThreadLocal<>();

    public static UserContext currentUser() {
        return context.get();
    }

    public static void set(UserContext userContext) {
        context.set(userContext);
    }

    public static void shutdown() {
        context.remove();
    }
}
