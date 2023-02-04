package frc.robot.subsystems;

import java.util.ArrayList;
import java.util.List;

import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Photonvision extends SubsystemBase {

    private final PhotonCamera photonCamera;
    private List<PhotonTrackedTarget> photonTrackedTargets;
    private int currentTargets = 0;

    public Photonvision (PhotonCamera photonCamera) {

        this.photonCamera = photonCamera;
    }

    public void periodic () {

        PhotonPipelineResult photonPipelineResult = this.photonCamera.getLatestResult();
        if (!photonPipelineResult.hasTargets()) {
            
            this.photonTrackedTargets.clear();
            this.currentTargets = 0;
        } else {

            this.photonTrackedTargets = photonPipelineResult.getTargets();
            this.currentTargets = this.photonTrackedTargets.size();
        }

        SmartDashboard.putNumber("Charging Station X", this.triangulatePosition().getX());
        SmartDashboard.putNumber("Charging Station Y", this.triangulatePosition().getY());
    }

    public Pose2d triangulatePosition () {

        List<Pose2d> positions = new ArrayList<Pose2d>();
        List<Double> errors = new ArrayList<Double>();

        for (PhotonTrackedTarget photonTrackedTarget : this.photonTrackedTargets) {

            positions.add(this.getRelativePosition(photonTrackedTarget));
            errors.add(photonTrackedTarget.getPoseAmbiguity());
        }

        return this.combinePositions(positions, errors);
    }

    public Pose2d getRelativePosition (PhotonTrackedTarget photonTrackedTarget) {

        double positionX = photonTrackedTarget.getBestCameraToTarget().getX();
        double positionY = photonTrackedTarget.getBestCameraToTarget().getY();

        int tagID = photonTrackedTarget.getFiducialId();
        double transformX = 0.0;
        double transformY = 0.0;

        if (tagID == 1) {

            transformX = Constants.AutonomousConstants.APRILTAG_POSITIONS.one.x;
            transformY = Constants.AutonomousConstants.APRILTAG_POSITIONS.one.y;
        } else if (tagID == 2) {

            transformX = Constants.AutonomousConstants.APRILTAG_POSITIONS.two.x;
            transformY = Constants.AutonomousConstants.APRILTAG_POSITIONS.two.y;
        } else if (tagID == 3) {

            transformX = Constants.AutonomousConstants.APRILTAG_POSITIONS.three.x;
            transformY = Constants.AutonomousConstants.APRILTAG_POSITIONS.three.y;
        }

        transformX *= -1;
        transformY *= -1;

        positionX += transformX;
        positionY += transformY;

        return new Pose2d(positionX, positionY, new Rotation2d());
    }

    public Pose2d combinePositions (List<Pose2d> positions, List<Double> errors) {

        double cumulativePositionX = 0.0;
        double cumulativePositionY = 0.0;
        double cumulativeError = 0.0;

        for (int i = 0; i < positions.size(); i++) {

            double positionX = positions.get(i).getX();
            double positionY = positions.get(i).getY();
            double error = errors.get(i);

            cumulativePositionX += (positionX * error);
            cumulativePositionY += (positionY * error);
            cumulativeError += error;
        }

        double positionX = cumulativePositionX / cumulativeError;
        double positionY = cumulativePositionY / cumulativeError;
        return new Pose2d(positionX, positionY, new Rotation2d());
    }
}
