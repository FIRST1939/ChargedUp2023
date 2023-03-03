package frc.robot.commands.autonomous;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.lib.PID;
import frc.robot.Constants;
import frc.robot.subsystems.WestCoastDrive;

public class BalanceChargingStation extends CommandBase {
    
    private final WestCoastDrive westCoastDrive;
    private final AHRS navX;
    
    private final PID balancePID;
    private int balancedTicks = 0;
    private boolean onStation = false;

    public BalanceChargingStation (WestCoastDrive westCoastDrive, AHRS navX) {

        this.westCoastDrive = westCoastDrive;
        this.navX = navX;

        this.balancePID = new PID(Constants.AutonomousConstants.BALANCE_KP, Constants.AutonomousConstants.BALANCE_KI, Constants.AutonomousConstants.BALANCE_KD, 15);
        this.addRequirements(this.westCoastDrive);
    }

    @Override
    public void initialize () { this.westCoastDrive.resetHeading(); }

    @Override
    public void execute () {

        if (!this.onStation) {

            if (this.navX.getPitch() < 6.0) { this.westCoastDrive.drive(0.35, 0); }
            else { this.onStation = true; }
            return;
        }

        double powerValue = this.balancePID.calculate(this.navX.getPitch());
        double turningValue = -this.westCoastDrive.getHeading() * Constants.AutonomousConstants.GYRO_STRAIGHT_KP;
        this.westCoastDrive.drive(powerValue, turningValue);

        if (Math.abs(this.navX.getPitch()) <= 3.0) { this.balancedTicks++; }
        else { this.balancedTicks = 0; } 
    }

    @Override
    public boolean isFinished () { return this.balancedTicks >= 75; }

    @Override
    public void end (boolean interrupted) {

        this.westCoastDrive.stop();
        if (!interrupted) { new TurnToRelativeAngle(this.westCoastDrive, -90).schedule(); }
    }
}
