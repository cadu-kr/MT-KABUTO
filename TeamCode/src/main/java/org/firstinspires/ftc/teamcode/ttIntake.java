package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "teste-intake-Cadu")
public class ttIntake extends OpMode {

    DcMotor intake;
    int op = 1;
    boolean aa = false;

    @Override
    public void init() {
        intake = hardwareMap.get(DcMotor.class, "intake");

        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    @Override
    public void loop() {

        if (gamepad1.left_bumper && !aa){
            op = op + 1;
        }
        if (op %2 == 0){
            intake.setPower(1);
        }
        aa = gamepad1.left_bumper;
    }
}
