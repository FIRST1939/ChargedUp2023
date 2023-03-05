package frc.robot.commands.autonomous.charging_station;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.WestCoastDrive;

public class MaintainChargingStation extends CommandBase {
    
    private final WestCoastDrive westCoastDrive;
    private final AHRS navX;

    public MaintainChargingStation (WestCoastDrive westCoastDrive, AHRS navX) {

        this.westCoastDrive = westCoastDrive;
        this.navX = navX;

        this.addRequirements(this.westCoastDrive);
    }

    @Override
    public void execute () {

        if (Math.abs(this.navX.getPitch()) >= 2.0) { this.westCoastDrive.drive(Math.signum(this.navX.getPitch()) * 0.18, 0.0); }
        else { this.westCoastDrive.drive(0.0, 0.0); }
    }

    @Override
    public boolean isFinished () { return false; }

    @Override
    public void end (boolean interrupted) { this.westCoastDrive.stop(); }
}
