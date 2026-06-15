package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Lancador{
    DcMotor motor1;
    Servo servo1;
    double power = 0.1;
    boolean UP = false;
    boolean Down = false;


    public void inicializar(HardwareMap hardwareMap) {

        motor1 = hardwareMap.get(DcMotor.class, "lancador");

        motor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        servo1 = hardwareMap.get(Servo.class, "alavanca");
        servo1.scaleRange(0, 270);
    }

    public void rodar(Gamepad gamepad2) {


        if (gamepad2.dpad_up && !UP) {
            power = power + 0.1;
        }
        UP = gamepad2.dpad_up;

        if (gamepad2.dpad_down && !Down) {
            power = power - 0.1;
        }
        Down = gamepad2.dpad_down;

        if (power > 1.0){
            power = 1.0;
        }
        if (power < 0.0){
            power = 0.0;
        }

        if (gamepad2.right_trigger >= 0.2){
            motor1.setPower(power);
        }
        else{
            motor1.setPower(0.2);
        }

        telemetry.addData("Potência", power);

        if (gamepad2.a){
            servo1.setPosition(0.3);
        }

        else{
            servo1.setPosition(0.1);

        }

        telemetry.update();

    }
}
