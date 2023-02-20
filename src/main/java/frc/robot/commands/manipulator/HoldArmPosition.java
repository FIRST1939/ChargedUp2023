package frc.robot.commands.manipulator;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.lib.PID;
import frc.robot.Constants;
import frc.robot.Constants.ManipulatorConstants.ARM_POSITIONS;
import frc.robot.subsystems.Manipulator;

public class HoldArmPosition extends CommandBase {
    
    private final Manipulator manipulator;

    private final PID armPID;
    private final int position;

    public HoldArmPosition (Manipulator manipulator, ARM_POSITIONS armPosition) {

        this.manipulator = manipulator;

        this.armPID = new PID(Constants.ManipulatorConstants.ARM_KP, Constants.ManipulatorConstants.ARM_KI, Constants.ManipulatorConstants.ARM_KD);
        this.position = armPosition.position;
    
        this.addRequirements(this.manipulator);
    }

    @Override
    public void execute () {

        double error = this.position - this.manipulator.getArmPosition();
        double armPower = this.armPID.calculate(error);
        this.manipulator.setArm(armPower);
    }
}
