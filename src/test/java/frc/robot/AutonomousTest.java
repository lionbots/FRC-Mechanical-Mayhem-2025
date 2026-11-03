package frc.robot;

// "assert" methods check that something is true. If the check fails, the test fails and JUnit
// prints a message explaining what went wrong.
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.wpi.first.hal.HAL;
import edu.wpi.first.wpilibj.simulation.DriverStationSim;
import edu.wpi.first.wpilibj.simulation.SimHooks;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.subsystems.DrivebaseSubsystem;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit test for the autonomous routine.
 *
 * <p>This test runs on your computer, not on the robot. WPILib provides a simulated robot (the
 * "HAL", or Hardware Abstraction Layer) so we can run our real robot code and check what it tells
 * the motors to do, without needing a roboRIO or any motors plugged in.
 *
 * <p>Run it from the project folder with: ./gradlew test
 */
class AutonomousTest {
  // How much simulated time passes in each loop. The real robot runs its code every 20 ms
  // (0.02 seconds), so we copy that here.
  private static final double DT = 0.02;

  // Decimal numbers (doubles) can have tiny rounding errors, so instead of checking that two
  // numbers are exactly equal, we check that they are within this small amount of each other.
  private static final double EPSILON = 1e-6;

  // RobotContainer creates all our subsystems and commands, just like it does on the real robot.
  private RobotContainer container;

  // @BeforeEach means JUnit runs this method before every @Test method, so each test starts with
  // a fresh, clean robot.
  @BeforeEach
  void setUp() {
    // Start the simulated robot hardware (the HAL). This must happen before we create any motors.
    //
    // HAL.initialize takes two numbers:
    //   500 - the timeout, in milliseconds. The HAL gets up to 500 ms (half a second) to start up
    //         before it gives up and reports a failure.
    //   0   - the mode, which decides what to do if another program is already using the HAL:
    //           0 = try to stop the other program, and report an error if that does not work
    //           1 = force the other program to stop
    //           2 = just print a warning and carry on (this can cause strange behavior)
    // WPILib's documentation lists 500 and 0 as the normal values, so you should not need to
    // change them.
    //
    // HAL.initialize returns true if startup worked. Wrapping it in assertTrue makes the test fail
    // right here, on this line, if the simulated hardware cannot start, instead of failing in a
    // confusing way later on.
    assertTrue(HAL.initialize(500, 0));

    // Freeze the simulated clock. Time will now only move forward when we tell it to (see
    // runFor below). This lets a "3 second" test finish almost instantly, and makes the test
    // give the same result every time.
    SimHooks.pauseTiming();

    // Pretend the Driver Station has put the robot in autonomous mode and enabled it. Commands do
    // not run while the robot is disabled, so this step is required.
    DriverStationSim.setAutonomous(true);
    DriverStationSim.setEnabled(true);
    DriverStationSim.notifyNewData();

    // Build the robot: subsystems, default commands, and controller bindings.
    container = new RobotContainer();
  }

  // @AfterEach means JUnit runs this method after every @Test method, even if the test failed.
  // It cleans up so one test cannot affect the next one.
  @AfterEach
  void tearDown() {
    // The CommandScheduler is shared by the whole program, so we stop every command and forget
    // every subsystem and button binding that this test created.
    CommandScheduler.getInstance().cancelAll();
    CommandScheduler.getInstance().unregisterAllSubsystems();
    CommandScheduler.getInstance().getActiveButtonLoop().clear();

    // Release the simulated motors so the next test can create motors with the same CAN IDs.
    container.close();

    // Disable the robot and let the simulated clock run normally again.
    DriverStationSim.setEnabled(false);
    DriverStationSim.notifyNewData();
    SimHooks.resumeTiming();
  }

  // @Test marks this method as a test for JUnit to run. The name describes what we expect to
  // happen, so a failing test tells you exactly which behavior broke.
  @Test
  void autoDrivesBackwardForThreeSecondsThenStops() {
    // Start the autonomous command, the same way Robot.autonomousInit() does on the real robot.
    CommandScheduler.getInstance().schedule(container.getAutonomousCommand());
    DrivebaseSubsystem drive = container.getDriveBase();

    // Let 2.9 seconds of simulated time pass. Autonomous should still be running.
    runFor(2.9);

    // Check that both wheels are being driven backward (a negative motor output means backward).
    assertTrue(drive.getLeftOutput() < 0, "left motor should drive backward");
    assertTrue(drive.getRightOutput() < 0, "right motor should drive backward");

    // Both sides should get the same output. If they did not, the robot would turn instead of
    // driving straight.
    assertEquals(drive.getLeftOutput(), drive.getRightOutput(), EPSILON);

    // Let another 0.2 seconds pass, taking us to 3.1 seconds, just past the 3 second time limit.
    runFor(0.2);

    // The autonomous command should have timed out and stopped the motors.
    assertEquals(0.0, drive.getLeftOutput(), EPSILON);
    assertEquals(0.0, drive.getRightOutput(), EPSILON);
  }

  /**
   * Moves simulated time forward, running the robot code the same way the real robot does.
   *
   * @param seconds how many seconds of simulated time should pass.
   */
  private void runFor(double seconds) {
    // Work out how many 20 ms loops fit into the requested time. For example, 2.9 seconds is
    // 145 loops.
    for (int i = 0; i < Math.round(seconds / DT); i++) {
      // Run one loop of the robot code: every scheduled command's execute() and isFinished()
      // get called, just like Robot.robotPeriodic() does every 20 ms on the real robot.
      CommandScheduler.getInstance().run();

      // Move the frozen simulated clock forward by 20 ms.
      SimHooks.stepTiming(DT);
    }
  }
}
