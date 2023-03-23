package frc.robot.commands.manipulator;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Manipulator;

public class ObtainPlatform extends CommandBase {
    
    private final Manipulator manipulator;
    private final PIDController armPID;

    public ObtainPlatform (Manipulator manipulator) {

        this.manipulator = manipulator;
        this.armPID = new PIDController(Constants.ManipulatorConstants.ARM_KP, Constants.ManipulatorConstants.ARM_KI, Constants.ManipulatorConstants.ARM_KD);
        this.armPID.setSetpoint(Constants.ManipulatorConstants.ARM_POSITIONS.PLATFORM.position);
        this.armPID.setTolerance(Constants.ManipulatorConstants.ARM_POSITIONS.PLATFORM.tolerance);

        this.addRequirements(this.manipulator);
    }

    @Override
    public void execute () {

        double armPower = this.armPID.calculate(this.manipulator.getArmPosition());

        this.manipulator.setArm(armPower);
        this.manipulator.setRollers(this.manipulator.getGamePiece() * 0.8);
    }
}
