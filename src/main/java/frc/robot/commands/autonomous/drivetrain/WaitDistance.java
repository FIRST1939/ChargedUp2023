package frc.robot.commands.autonomous.drivetrain;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.WestCoastDrive;

public class WaitDistance extends CommandBase {
    
    private final BooleanSupplier booleanSupplier;

    public WaitDistance (WestCoastDrive westCoastDrive, double meters) { this.booleanSupplier = () -> Math.abs(westCoastDrive.getAverageDistance()) >= Math.abs(meters); }

    @Override
    public boolean isFinished () { return this.booleanSupplier.getAsBoolean(); }
}
