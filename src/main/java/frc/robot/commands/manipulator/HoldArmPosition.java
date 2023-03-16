package frc.robot.commands.manipulator;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Constants.ManipulatorConstants.ARM_POSITIONS;
import frc.robot.subsystems.Manipulator;

public class HoldArmPosition extends CommandBase {
    
    private final Manipulator manipulator;
    private final PIDController armPID;

    public HoldArmPosition (Manipulator manipulator, ARM_POSITIONS armPosition) {

        this.manipulator = manipulator;
        this.armPID = new PIDController(Constants.ManipulatorConstants.ARM_KP, Constants.ManipulatorConstants.ARM_KI, Constants.ManipulatorConstants.ARM_KD);
        this.armPID.setSetpoint(armPosition.position);
        this.armPID.setTolerance(armPosition.tolerance);

        this.addRequirements(this.manipulator);
    }

    @Override
    public void initialize () { 
    
        if (this.manipulator.getUsedPID()) { this.cancel(); }
        this.manipulator.setUsedPID(true);
    }

    public void periodic () { SmartDashboard.putData("Arm PID", this.armPID); }

    @Override
    public void execute () {

        double armPower = this.armPID.calculate(this.manipulator.getArmPosition());
        this.manipulator.setArm(armPower);
    }
}
