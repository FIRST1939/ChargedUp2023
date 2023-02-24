package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {

    private final WPI_TalonFX sliderMotor;
    private final CANSparkMax rollerMotor;

    public Intake () {

        this.sliderMotor = new WPI_TalonFX(Constants.IntakeConstants.SLIDER_MOTOR);
        this.rollerMotor = new CANSparkMax(Constants.IntakeConstants.ROLLER_MOTOR, MotorType.kBrushless);

        this.sliderMotor.configFactoryDefault();
        this.sliderMotor.setNeutralMode(NeutralMode.Brake);
        this.sliderMotor.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 30);
        this.sliderMotor.configNominalOutputForward(0,30);
        this.sliderMotor.configNominalOutputReverse(0,30);
        this.sliderMotor.configPeakOutputForward(1,30);
        this.sliderMotor.configPeakOutputReverse(-1,30);

        this.rollerMotor.restoreFactoryDefaults();
        this.rollerMotor.setIdleMode(IdleMode.kBrake);
    }

    public void periodic () { SmartDashboard.putNumber("Slider Position", this.getSliderPosition()); }

    /**
     * Sets the slider motor to the given velocity, based upon input from the XBox Controller.
     * All inputs are capped at ~20% power for safety reasons.
     */
    public void setSlider (double velocity) { 
        
        boolean beyondLimit = false;

        if (velocity < 0 && Constants.IntakeConstants.SLIDER_MINIMUM_EXTENSION >= this.getSliderPosition()) { beyondLimit = true; }
        if (velocity > 0 && Constants.IntakeConstants.SLIDER_MAXIMUM_EXTENSION <= this.getSliderPosition()) { beyondLimit = true; }

        if (!beyondLimit) { this.sliderMotor.set(velocity / 5.0); } 
        else { this.sliderMotor.set(0.0); }
    }

    /**
     * Sets the roller motor to the given velocity, based upon input from the XBox Controller.
     * All inputs are capped at ~60% power for safety reasons.
     */
    public void setRollers (double velocity) { this.rollerMotor.set(volocity / 1.5); }

    public double getSliderPosition () { return this.sliderMotor.getSelectedSensorPosition(); }
    public void zeroSlider () { this.sliderMotor.setSelectedSensorPosition(0.0); }
}
