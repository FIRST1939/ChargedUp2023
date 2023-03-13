package frc.robot.commands.manipulator;

import java.util.Map;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Constants.ManipulatorConstants.ARM_POSITIONS;
import frc.robot.subsystems.Manipulator;

public class HoldArmPosition extends CommandBase {
    
    private final Manipulator manipulator;

    private final PIDController armPID;
    private final int position;

    private GenericEntry armPowerEntry;

    public HoldArmPosition (Manipulator manipulator, ARM_POSITIONS armPosition) {

        this.manipulator = manipulator;
        this.armPID = new PIDController(Constants.ManipulatorConstants.ARM_KP, Constants.ManipulatorConstants.ARM_KI, Constants.ManipulatorConstants.ARM_KD);
        this.position = armPosition.position;
    
        this.addRequirements(this.manipulator);
    }

    @Override
    public void initialize () { 
    
        if (this.manipulator.getUsedPID()) { this.cancel(); }
        this.manipulator.setUsedPID(true); 

        this.armPowerEntry = Shuffleboard.getTab("Arm Tuning")
            .add("Arm Power", 0.0)
            .withWidget(BuiltInWidgets.kDial)
            .withPosition(3, 0)
            .withSize(2, 2)
            .withProperties(Map.of("min", -1.0, "max", 1.0))
            .getEntry();
    }

    @Override
    public void execute () {

        double error = this.position - this.manipulator.getArmPosition();
        double armPower = this.armPID.calculate(this.manipulator.getArmPosition(), this.position);

        // Gradients the Initial 30,000 Encoder Clicks
        if (error > 30000) { armPower *= (30000 / error); }
        this.manipulator.setArm(armPower);

        this.armPowerEntry.setDouble(armPower);
    }
}
