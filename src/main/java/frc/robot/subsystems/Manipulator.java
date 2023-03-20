package frc.robot.subsystems;

import java.util.Map;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticHub;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Manipulator extends SubsystemBase {
    
    private static Manipulator manipulatorInstance = null;

    private final WPI_TalonFX armMotor;
    private final CANSparkMax rollerMotor;
    
    private final GenericEntry armPositionEntry;
    private final GenericEntry armLimitSwitchEntry;

    public final DigitalInput armLimitSwitch;
    private final DoubleSolenoid airLockPiston;
    private boolean isAirLockPistonExtended = false;

    private boolean usedPID = false;
    private int gamePiece = 0;

    private GenericEntry armPositionEntry;

    public Manipulator () {

        this.armMotor = new WPI_TalonFX(Constants.ManipulatorConstants.ARM_MOTOR);
        this.rollerMotor = new CANSparkMax(Constants.ManipulatorConstants.ROLLER_MOTOR, MotorType.kBrushless);
        this.armLimitSwitch = new DigitalInput(Constants.ManipulatorConstants.ARM_LIMIT_SWITCH);
        this.airLockPiston = new DoubleSolenoid(Constants.ElectronicConstants.PNEUMATICS_HUB, PneumaticsModuleType.REVPH, Constants.ManipulatorConstants.AIR_LOCK_PISTON_FORWARD, Constants.ManipulatorConstants.AIR_LOCK_PISTON_REVERSE);

        this.armMotor.configFactoryDefault();
        this.armMotor.setNeutralMode(NeutralMode.Brake);
        this.armMotor.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 30);
        this.armMotor.configNominalOutputForward(0,30);
        this.armMotor.configNominalOutputReverse(0,30);
        this.armMotor.configPeakOutputForward(1,30);
        this.armMotor.configPeakOutputReverse(-1,30);

        this.rollerMotor.restoreFactoryDefaults();
        this.rollerMotor.setIdleMode(IdleMode.kBrake);

        this.armPositionEntry = Shuffleboard.getTab("Competition")
            .add("Arm Position", 0.0)
            .withWidget(BuiltInWidgets.kTextView)
            .withPosition(8, 4)
            .withSize(2, 1)
            .getEntry();

        this.armLimitSwitchEntry = Shuffleboard.getTab("Competition")
            .add("Arm Limit Switch", false)
            .withWidget(BuiltInWidgets.kBooleanBox)
            .withProperties(Map.of("COLOR WHEN TRUE", "lime", "COLOR WHEN FALSE", "dark red"))
            .withPosition(6, 4)
            .withSize(2, 1)
            .getEntry();

        this.armLimitSwitch = new DigitalInput(Constants.ManipulatorConstants.ARM_LIMIT_SWITCH);
        //this.coneBeamBreak = new DigitalInput(Constants.ManipulatorConstants.CONE_BEAM_BREAK);
        //this.cubeBeamBreak = new DigitalInput(Constants.ManipulatorConstants.CUBE_BEAM_BREAK);
        
        this.armPositionEntry = Shuffleboard.getTab("Arm Tuning")
            .add("Arm Position", this.getArmPosition())
            .withWidget(BuiltInWidgets.kGraph)
            .withPosition(0, 0)
            .withSize(3, 3)
            .withProperties(Map.of("visible time", 30, "lower bound", -10000, "upper bound", 164000, "automatic bounds", false, "unit", "Encoder Clicks"))
            .getEntry();
    }

    public static Manipulator getInstance () {

        if (manipulatorInstance == null) { manipulatorInstance = new Manipulator(); }
        return manipulatorInstance;
    }
 
    public void periodic () { 
        
        this.armPositionEntry.setDouble(this.getArmPosition());
        this.armLimitSwitchEntry.setBoolean(this.armLimitSwitch.get());

        if (this.armLimitSwitch.get()) { 
            
            this.zeroArm(); 
            this.setUsedPID(false);
        }
    }

    /**
     * Sets the arm motor to the given velocity, based upon input from the XBox Controller.
     * All inputs are capped at ~60% power for safety reasons.
     */
    public void setArm (double velocity) { 

        if (Math.abs(velocity) > 1.0) { velocity = Math.signum(velocity) * 1.0; }

        if ((velocity < 0 && !this.armLimitSwitch.get()) || (velocity > 0)) { 
            
            if (this.getAirLock()) { this.setAirLock(false); }
            else { this.armMotor.set(velocity / 1.75); }
        } 
        else { 
            
            this.armMotor.set(0.0); 
        }
    }

    /**
     * Sets the roller motor to the given velocity, based upon input from the XBox Controller.
     * All inputs are capped at ~70% power for safety reasons.
     */
    public void setRollers (double velocity) { this.rollerMotor.set(velocity / 1.4); }

    public double getArmPosition () { return this.armMotor.getSelectedSensorPosition(); }
    public void zeroArm () { this.armMotor.setSelectedSensorPosition(0.0); }

    public void setAirLock (Boolean airLock) { 
        
        DoubleSolenoid.Value pistonValue;

        if (airLock != null) {

            if (airLock.equals(true)) { 
                
                pistonValue = DoubleSolenoid.Value.kForward; 
                this.isAirLockPistonExtended = true;
            }

            else { 
                
                pistonValue = DoubleSolenoid.Value.kReverse; 
                this.isAirLockPistonExtended = false;
            }
        } else { 
            
            pistonValue = DoubleSolenoid.Value.kOff; 
        }
        SmartDashboard.putString("airLock", pistonValue.toString());
        this.airLockPiston.set(pistonValue);
    }

    public boolean getAirLock () { return this.isAirLockPistonExtended; }
    
    public void setGamePiece (int gamePiece) { this.gamePiece = gamePiece; }
    public int getGamePiece () { return this.gamePiece; }

    public void setUsedPID (boolean usedPID) { this.usedPID = usedPID; }
    public boolean getUsedPID () { return this.usedPID; }
    */
}
