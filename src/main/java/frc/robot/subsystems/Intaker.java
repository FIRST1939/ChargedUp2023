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

public class Intaker extends SubsystemBase {

    private static Intaker intakerInstance = null;

    private final WPI_TalonFX sliderMotor;
    private final CANSparkMax rollerMotor;

    private final DigitalInput sliderLimitSwitch;

    public Intaker () {

        this.sliderMotor = new WPI_TalonFX(Constants.IntakerConstants.SLIDER_MOTOR);
        this.rollerMotor = new CANSparkMax(Constants.IntakerConstants.ROLLER_MOTOR, MotorType.kBrushed);

        this.sliderMotor.configFactoryDefault();
        this.sliderMotor.setNeutralMode(NeutralMode.Brake);
        this.sliderMotor.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 30);
        this.sliderMotor.configNominalOutputForward(0,30);
        this.sliderMotor.configNominalOutputReverse(0,30);
        this.sliderMotor.configPeakOutputForward(1,30);
        this.sliderMotor.configPeakOutputReverse(-1,30);

        this.rollerMotor.restoreFactoryDefaults();
        this.rollerMotor.setIdleMode(IdleMode.kBrake);

        this.sliderLimitSwitch = new DigitalInput(Constants.IntakerConstants.SLIDER_LIMIT_SWITCH);
    }

    public static Intaker getInstance () {

        if (intakerInstance == null) { intakerInstance = new Intaker(); }
        return intakerInstance;
    }

    public void periodic () { 
        
        SmartDashboard.putNumber("Slider Position", this.getSliderPosition()); 
        SmartDashboard.putBoolean("Slider Limit Switch", this.sliderLimitSwitch.get());
        if (this.sliderLimitSwitch.get()) { this.zeroSlider(); }
    }

    /**
     * Sets the slider motor to the given velocity, based upon input from the XBox Controller.
     * All inputs are capped at ~30% power for safety reasons.
     */
    public void setSlider (double velocity) { 
        
        if ((velocity < 0 && !this.sliderLimitSwitch.get()) || (velocity > 0)) { this.sliderMotor.set(velocity / 3.6); } 
        else { this.sliderMotor.set(0.0); }
    }

    /**
     * Sets the roller motor to the given velocity, based upon input from the XBox Controller.
     * All inputs are capped at ~60% power for safety reasons.
     */
    public void setRollers (double velocity) { this.rollerMotor.set(velocity / 1.65); }

    public double getSliderPosition () { return this.sliderMotor.getSelectedSensorPosition(); }
    public void zeroSlider () { this.sliderMotor.setSelectedSensorPosition(0.0); }
}
