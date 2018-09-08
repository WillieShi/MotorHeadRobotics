package legacy;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;

@Autonomous(name="telemtry", group="ForceofHaskins")
@Disabled
public class telemetry extends LinearOpMode
{
    /* Declare OpMode members. */
    ForceofHaskinsHardware robot   = new ForceofHaskinsHardware();   // Use a Pushbot's hardware
    private ElapsedTime runtime = new ElapsedTime();

    static final double     COUNTS_PER_MOTOR_REV    = 1120 ;    // eg: AndyMark Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 1.0 ;     // This is < 1.0 if geared UP //On OUR CENTER MOTOR THE GEAR REDUCTION IS .5
    static final double     WHEEL_DIAMETER_INCHES   = 4.0 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double     DRIVE_SPEED             = 0.3;

    //Vuforia Setup
    public static final String TAG = "Vuforia VuMark Sample";
    OpenGLMatrix lastLocation = null;
    VuforiaLocalizer vuforia;

    @Override
    public void runOpMode()
    {

        /*
         * Initialize the drive system variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);

        boolean redTouch = robot.touchRed.getState();
        boolean blueTouch = robot.touchBlue.getState();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        runtime.reset();
        while (runtime.seconds() < 10)
        {
            redTouch = robot.touchRed.getState();
            blueTouch = robot.touchBlue.getState();

            telemetry.addData("Red Touch", redTouch);
            telemetry.addData("Blue Touch", blueTouch);
            telemetry.update();
        }

        double bluevalue = 0;
        double postrack = .56;
        //Jewel Capture Code
        for(int i = 0; i < 5; i++)
        {
            bluevalue = bluevalue + robot.sensorColor.blue();
            postrack = postrack + .005;
            robot.colorServo.setPosition(postrack);

            sleep(300);
        }
        bluevalue = bluevalue/5;

        //telemetry.addData("BlueTotal:", bluevalue);
        telemetry.update();
        /*
        else
        {

            encoderDrive(-5, -5, 0, 2.0); //If it's not red than red is on the opposite side drive backward 5 inches
            encoderDrive(12, 12, 0, 2.0); //Get in position for Vuforia reading

        }
        */

    }

    /*
     *  Method to perfmorm a relative move, based on encoder counts.
     *  Encoders are not reset as the move is based on the current position.
     *  Move will stop if any of three conditions occur:
     *  1) Move gets to the desired position
     *  2) Move runs out of time
     *  3) Driver stops the opmode running.
     */

}