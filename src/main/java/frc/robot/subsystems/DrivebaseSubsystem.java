// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

// Imports the libraries need for the code, including the DifferentialDrive and the SparkMax
// objects.
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;

// Class to define the DrivebaseSubsystem, named after the Java file.
public class DrivebaseSubsystem extends SubsystemBase {
  // Sets up the 2 motors as instances of SparkMax.
  private final SparkMax rightfrontWheel =
      new SparkMax(DriveConstants.rightfrontWheel, MotorType.kBrushless);
  private final SparkMax leftfrontWheel =
      new SparkMax(DriveConstants.leftfrontWheel, MotorType.kBrushless);
  // Sets up a differential drive.
  private final DifferentialDrive differentialDrive =
      new DifferentialDrive(leftfrontWheel, rightfrontWheel);

  // Physics simulation of the drivetrain. It is only used on a computer (in the simulator and in
  // unit tests), never on the real robot. It works out where the robot would move based on the
  // voltage we send to each side.
  private final DifferentialDrivetrainSim driveSim = new DifferentialDrivetrainSim(
      edu.wpi.first.math.system.plant.DCMotor.getNEO(2),
      DriveConstants.kGearRatio,
      DriveConstants.kMomentOfInertia,
      DriveConstants.kRobotMassKg,
      DriveConstants.kWheelRadiusMeters,
      DriveConstants.kTrackWidthMeters,
      null
  );

  // A field map that shows the simulated robot's position in the simulator window.
  private final Field2d field = new Field2d();

  /** Creates a new DrivebaseSubsystem. */
  public DrivebaseSubsystem() {
    setMotorInverted();
    // Sends the field map to the dashboard so it appears in the simulator window.
    SmartDashboard.putData("Field", field);
  }

  // Sets the left wheel inverted using a config
  public void setMotorInverted() {
    SparkMaxConfig config = new SparkMaxConfig();
    config.inverted(true);
    leftfrontWheel.configure(
        config, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
  }

  public void arcadeDrive(double speed, double angle) {
    this.differentialDrive.arcadeDrive(speed, angle);
  }

  // Creates a method to easily set the motor speeds.
  public void setMotorSpeed(double leftSpeed, double rightSpeed) {
    rightfrontWheel.set(rightSpeed);
    leftfrontWheel.set(leftSpeed);
  }

  public double getLeftOutput() {
    return leftfrontWheel.get();
  }

  public double getRightOutput() {
    return rightfrontWheel.get();
  }

  /**
   * Returns where the simulated robot is on the field: its x and y position in meters, and the
   * direction it is facing. The robot starts at x = 0, y = 0, facing 0 degrees. Driving forward
   * makes x bigger, driving backward makes x smaller.
   *
   * @return the simulated robot's position.
   */
  public Pose2d getPose() {
    // TODO (students) step 4: Return the simulation's position instead of this placeholder.
    //   Hint: DifferentialDrivetrainSim has a getPose() method.
    return new Pose2d();
  }

  public void close() {
    differentialDrive.close();
    leftfrontWheel.close();
    rightfrontWheel.close();
  }

  /**
   * An example method querying a boolean state of the subsystem (for example, a digital sensor).
   *
   * @return value of some boolean subsystem state, such as a digital sensor.
   */
  public boolean exampleCondition() {
    // Query some boolean state, such as a digital sensor.
    return false;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation

    // Until step 1 is done there is no simulation yet, so there is nothing to update.
    if (driveSim == null) {
      return;
    }

    // TODO (students) step 2: Tell the simulation what voltage each side of the drivetrain gets.
    //   - Motor output is a number from -1 to 1 (getLeftOutput() and getRightOutput()).
    //   - Voltage = motor output * battery voltage. RobotController.getBatteryVoltage() gives the
    //     battery voltage. (import edu.wpi.first.wpilibj.RobotController)
    //   - Pass both voltages to driveSim.setInputs(leftVolts, rightVolts).
    //   - Do NOT flip the sign of the left side here. The left motor is inverted inside the
    //     SparkMax itself, so getLeftOutput() already means "positive = forward".
    double leftVolts = getLeftOutput() * edu.wpi.first.wpilibj.RobotController.getBatteryVoltage();
    double rightVolts = getRightOutput() * edu.wpi.first.wpilibj.RobotController.getBatteryVoltage();
    driveSim.setInputs(leftVolts, rightVolts);

    // TODO (students) step 3: Move the simulation forward by one robot loop (0.02 seconds).
    //   Hint: driveSim.update(...)
    driveSim.update(0.02);

    // TODO (students) step 5: Show the robot on the field map in the simulator window.
    //   Hint: field.setRobotPose(...) with the position from getPose().

    // Note: the simulation does not model static friction (the extra push a real robot needs to
    // start moving). It will show the robot moving even at very low power, so it cannot tell us
    // whether a low output (like the ~8% autonomous power) is enough to move the real robot.
  }
}
