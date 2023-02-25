package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Manipulator extends SubsystemBase {
    
    private final WPI_TalonFX armMotor;
    private final CANSparkMax rollerMotor;

    private final DigitalInput coneBeamBreak;
    private final DigitalInput cubeBeamBreak;

    public Manipulator () {

        this.armMotor = new WPI_TalonFX(Constants.ManipulatorConstants.ARM_MOTOR);
        this.rollerMotor = new CANSparkMax(Constants.ManipulatorConstants.ROLLER_MOTOR, MotorType.kBrushless);

        this.armMotor.configFactoryDefault();
        this.armMotor.setNeutralMode(NeutralMode.Brake);
        this.armMotor.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 30);
        this.armMotor.configNominalOutputForward(0,30);
        this.armMotor.configNominalOutputReverse(0,30);
        this.armMotor.configPeakOutputForward(1,30);
        this.armMotor.configPeakOutputReverse(-1,30);

        this.rollerMotor.restoreFactoryDefaults();
        this.rollerMotor.setIdleMode(IdleMode.kBrake);

        this.coneBeamBreak = new DigitalInput(Constants.ManipulatorConstants.CONE_BEAM_BREAK);
        this.cubeBeamBreak = new DigitalInput(Constants.ManipulatorConstants.CUBE_BEAM_BREAK);
    }

    public void periodic () { SmartDashboard.putNumber("Arm Position", this.getArmPosition()); }

    /**
     * Sets the arm motor to the given velocity, based upon input from the XBox Controller.
     * All inputs are capped at ~20% power for safety reasons.
     */
    public void setArm (double velocity) { 
        
        boolean beyondLimit = false;

        if (velocity < 0 && Constants.ManipulatorConstants.ARM_MINIMUM_EXTENSION >= this.getArmPosition()) { beyondLimit = true; }
        if (velocity > 0 && Constants.ManipulatorConstants.ARM_MAXIMUM_EXTENSION <= this.getArmPosition()) { beyondLimit = true; }

        if (!beyondLimit) { this.armMotor.set(velocity / 5.0); } 
        else { this.armMotor.set(0.0); }
    }

    /**
     * Sets the roller motor to the given velocity, based upon input from the XBox Controller.
     * All inputs are capped at ~70% power for safety reasons.
     */
    public void setRollers (double velocity) { this.rollerMotor.set(velocity / 1.4); }
    public boolean isHoldingCone () { return !this.coneBeamBreak.get(); }
    public boolean isHoldingCube () { return !this.cubeBeamBreak.get(); }

    public double getArmPosition () { return this.armMotor.getSelectedSensorPosition(); }
    public void zeroArm () { this.armMotor.setSelectedSensorPosition(0.0); }
}
