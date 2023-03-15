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

    public HoldArmPosition (Manipulator manipulator, int armPosition, boolean useIntegral) {

        this.manipulator = manipulator;
        this.armPID = new PID(Constants.ManipulatorConstants.ARM_KP, Constants.ManipulatorConstants.ARM_KI, Constants.ManipulatorConstants.ARM_KD, 30000, useIntegral);
        this.position = armPosition;

        this.addRequirements(this.manipulator);
    }

    public HoldArmPosition (Manipulator manipulator, ARM_POSITIONS armPosition) {

        this.manipulator = manipulator;
        this.armPID = new PID(Constants.ManipulatorConstants.ARM_KP, Constants.ManipulatorConstants.ARM_KI, Constants.ManipulatorConstants.ARM_KD, 30000, armPosition.useIntegral);
        this.position = armPosition.position;
    
        this.addRequirements(this.manipulator);
    }

    @Override
    public void initialize () { 
    
        /**
        if (this.manipulator.getUsedPID()) { this.cancel(); }
        this.manipulator.setUsedPID(true); 
        */
    }

    @Override
    public void execute () {

        /** 
        double error = this.position - this.manipulator.getArmPosition();
        double armPower = this.armPID.calculate(error);

        if (error > 30000) { armPower *= (30000 / error); }
        this.manipulator.setArm(armPower);
        */
    }
}
