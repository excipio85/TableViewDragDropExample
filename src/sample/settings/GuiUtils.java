package sample.settings;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * Created by Joachim on 20.08.2015.
 */
public class GuiUtils {

    private GuiUtils(){}

    private static BooleanProperty animationActivated = new SimpleBooleanProperty(Boolean.TRUE);

    public static boolean isAnimationActivated(){
        return animationActivated.get();
    }

    public static void setAnimationActivated(boolean isAnimationActivated){
        animationActivated.set(isAnimationActivated);
    }
}
