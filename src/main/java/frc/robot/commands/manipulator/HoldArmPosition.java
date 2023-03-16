package frc.robot.commands.manipulator;

import java.util.Map;

import javax.swing.text.Position;
import javax.swing.text.StyleContext.SmallAttributeSet;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.DoubleSubscriber;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Constants.ManipulatorConstants.ARM_POSITIONS;
import frc.robot.subsystems.Manipulator;

public class HoldArmPosition extends CommandBase {
    
    private final Manipulator manipulator;
    private final PIDController armPID;
    //private ShuffleboardTab tab = Shuffleboard.getTab("Arm Tuning");
    


    //private final double setPoint;

    public HoldArmPosition (Manipulator manipulator, ARM_POSITIONS armPosition) {
      //  NetworkTableInstance inst = NetworkTableInstance.getDefault();
      //  NetworkTable datatable = inst.getTable("Arm");
      //  goal = datatable.getDoubleTopic(armPosition.name).subscribe(armPosition.position);

        this.manipulator = manipulator;
        this.armPID = new PIDController(Constants.ManipulatorConstants.ARM_KP, Constants.ManipulatorConstants.ARM_KI, Constants.ManipulatorConstants.ARM_KD);
        
        double position= armPosition.position;
        SmartDashboard.putNumber(armPosition.name, position);
        this.armPID.setSetpoint(position);
        this.armPID.setTolerance(1000);
        this.addRequirements(this.manipulator);
    }

    @Override
    public void initialize () { 
    
        // if (this.manipulator.getUsedPID()) { this.cancel(); }
        // this.manipulator.setUsedPID(true); 

        // Shuffleboard.getTab("Arm Tuning")
        //     .addDouble("Arm Power", () -> this.armPID.calculate(this.manipulator.getArmPosition()))
        //     .withWidget(BuiltInWidgets.kDial)
        //     .withPosition(5, 0)
        //     .withSize(2, 2)
        //     .withProperties(Map.of("min", -1.0, "max", 1.0));
        // try{
        //  Shuffleboard.getTab("Arm Tuning")
        //      .add("Arm PID", this.armPID)
        //      .withWidget(BuiltInWidgets.kPIDController)
        //      .withPosition(0, 0)
        //      .withSize(2, 3);
        // }
      
    }
    public void periodic()
    {
        SmartDashboard.putData(armPID);
    }
    @Override
    public void execute () {

        //double error = this.setPoint - this.manipulator.getArmPosition();
        double armPower = this.armPID.calculate(this.manipulator.getArmPosition());
        this.manipulator.setArm(armPower);
    }

}
