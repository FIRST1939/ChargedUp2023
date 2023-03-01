package frc.robot.commands.manipulator;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Manipulator;

public class SetGamePiece extends InstantCommand {
    
    private final Manipulator manipulator;
    private final int gamePiece;

    public SetGamePiece (Manipulator manipulator, int gamePiece) {

        this.manipulator = manipulator;
        this.gamePiece = gamePiece;

        this.addRequirements(this.manipulator);
    }

    @Override
    public void initialize () { this.manipulator.setGamePiece(this.gamePiece); }
}
