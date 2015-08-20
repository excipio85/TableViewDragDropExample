package settings;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * Created by Joachim on 20.08.2015.
 */
public final class GuiUtils {

    // no instance of this class can be created
    private GuiUtils(){}

    //region Animation
    private static BooleanProperty animationActivated = new SimpleBooleanProperty(Boolean.TRUE);

    public static boolean isAnimationActivated(){
        return animationActivated.get();
    }

    public static void setAnimationActivated(boolean isAnimationActivated){
        animationActivated.set(isAnimationActivated);
    }
    //endregion

}
