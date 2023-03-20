package frc.robot.subsystems;

import java.util.Map;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import frc.robot.Constants;

public class Pneumatics extends SubsystemBase {
    
    private final Compressor compressor;
    private final GenericEntry compressorPressureEntry;

    public Pneumatics () {

        this.compressor = new Compressor(Constants.ElectronicConstants.PNEUMATICS_HUB, PneumaticsModuleType.REVPH);
        this.compressor.enableAnalog(Constants.ElectronicConstants.PNEUMATICS_MINIMUM_PRESSURE, Constants.ElectronicConstants.PNEUMATICS_MAXIMUM_PRESSURE);

        this.compressorPressureEntry = Shuffleboard.getTab("Competition")
            .add("Compressor Pressure", 0.0)
            .withWidget(BuiltInWidgets.kNumberBar)
            .withProperties(Map.of("MIN", 0.0, "MAX", Constants.ElectronicConstants.PNEUMATICS_MAXIMUM_PRESSURE, "CENTER", Constants.ElectronicConstants.PNEUMATICS_MINIMUM_PRESSURE))
            .withPosition(0, 2)
            .withSize(2, 1)
            .getEntry();
    }

    public void periodic () { this.compressorPressureEntry.setDouble(this.getCompressorPressure()); }
    public double getCompressorPressure () { return this.compressor.getPressure(); }
}
