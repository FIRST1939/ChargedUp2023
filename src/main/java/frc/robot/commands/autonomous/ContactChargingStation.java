package frc.robot.commands.autonomous;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.WestCoastDrive;

public class ContactChargingStation extends CommandBase {
    
    private final WestCoastDrive westCoastDrive;
    private final AHRS navX;

    public ContactChargingStation (WestCoastDrive westCoastDrive, AHRS navX) {

        this.westCoastDrive = westCoastDrive;
        this.navX = navX;

        this.addRequirements(this.westCoastDrive);
    }

    @Override
    public void initialize () { this.westCoastDrive.resetHeading(); }

    @Override
    public void execute () { this.westCoastDrive.drive(0.5, 0); }

    @Override
    public boolean isFinished () { return this.navX.getPitch() >= 16; }

    @Override
    public void end (boolean interrupted) { this.westCoastDrive.stop(); }
}
