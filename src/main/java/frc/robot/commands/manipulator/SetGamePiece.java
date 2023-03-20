package frc.robot.commands.manipulator;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Constants;
import frc.robot.Constants.ElectronicConstants.LED_COLORS;
import frc.robot.commands.SetLEDs;
import frc.robot.subsystems.LEDs;
import frc.robot.subsystems.Manipulator;

public class SetGamePiece extends InstantCommand {
    
    private final Manipulator manipulator;
    private final LEDs leds;

    private final int gamePiece;

    public SetGamePiece (Manipulator manipulator, LEDs leds, int gamePiece) {

        this.manipulator = manipulator;
        this.leds = leds;

        this.gamePiece = gamePiece;
        this.addRequirements(this.manipulator);
    }

    /** 
    @Override
    public void initialize () { 
        
        this.manipulator.setGamePiece(this.gamePiece);
        LED_COLORS ledColor = null;

        switch (this.gamePiece) {

            case -1: 
                ledColor = Constants.ElectronicConstants.LED_COLORS.CONE;
                break;

            case 1: 
                ledColor = Constants.ElectronicConstants.LED_COLORS.CUBE;
                break;

            case 0: 
                ledColor = Constants.ElectronicConstants.LED_COLORS.RAINBOW;
                break;
        }

        new SetLEDs(this.leds, ledColor).schedule();
    }
    */
}
