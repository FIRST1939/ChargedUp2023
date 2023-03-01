package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Indexer extends SubsystemBase {
    
    private final WPI_TalonFX indexerMotor;

    public Indexer () {

        this.indexerMotor = new WPI_TalonFX(Constants.IndexerConstants.INDEXER_MOTOR);
        this.indexerMotor.configFactoryDefault();
        this.indexerMotor.setNeutralMode(NeutralMode.Brake);
        this.indexerMotor.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 30);
        this.indexerMotor.configNominalOutputForward(0,30);
        this.indexerMotor.configNominalOutputReverse(0,30);
        this.indexerMotor.configPeakOutputForward(1,30);
        this.indexerMotor.configPeakOutputReverse(-1,30);
    }

    public void periodic () { SmartDashboard.putNumber("Indexer Postion", this.getIndexerPosition()); }

    /**
     * Sets the indexer motor to the given velocity, based upon input from the XBox Controller.
     * All inputs are capped at ~25% power for safety reasons.
     */
    public void setIndexer (double velocity) { this.indexerMotor.set(velocity / 4.0); }

    public double getIndexerPosition () { return this.indexerMotor.getSelectedSensorPosition(); }
    public void zeroIndexer () { this.indexerMotor.setSelectedSensorPosition(0.0); }
}
