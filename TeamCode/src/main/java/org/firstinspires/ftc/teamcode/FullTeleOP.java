package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "TT-Torreta")
public class FullTeleOP extends LinearOpMode {
    //Variaveis LANCADOR---------
    DcMotor lancador;
    double pw = 0;
    boolean ad = false;
    boolean rm = false;
    //---------------------------

    //Variaveis INTAKE------
    DcMotor intake;
    boolean aa = false;
    int op = 1;
    //----------------------

    //Variaveis RODAS------------
    private DcMotor rodaFD, rodaFE, rodaTD, rodaTE;
    //---------------------------

    //Variaveis TORRETA-----------
    DcMotor torreta;
    static final double rt = 288;
    static final int lmin = 0;
    static final int lmax = (int) rt;
    //-----------------------------


    public void runOpMode(){
        //HARDWAREMAP RODAS -----------------
        rodaFD = hardwareMap.get(DcMotor.class, "MotorFrenteDireita");
        rodaFE = hardwareMap.get(DcMotor.class, "MotorFrenteEsquerda");
        rodaTD = hardwareMap.get(DcMotor.class, "MotorTrasDireita");
        rodaTE = hardwareMap.get(DcMotor.class, "MotorTrasEsquerda");

        rodaFD.setDirection(DcMotorSimple.Direction.REVERSE);
        rodaTD.setDirection(DcMotorSimple.Direction.REVERSE);

        rodaFD.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rodaFE.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rodaTD.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rodaTE.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //------------------------------------

        //HARDWAREMAP INTAKE -----------------
        intake = hardwareMap.get(DcMotor.class, "intake");
        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //------------------------------------

        //HARDWAREMAP LANCADOR -----------------
        lancador = hardwareMap.get(DcMotor.class, "lancador");
        lancador.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //--------------------------------------

        //HARDWAREMAP TORRETA -----------------
        torreta = hardwareMap.get(DcMotor.class, "torreta");
        torreta.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        torreta.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        torreta.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //-------------------------------------

        waitForStart();

        while (opModeIsActive()) {

            //Codigo RODAS---------------------
            double comandoFrente  = -gamepad1.left_stick_y;
            double comandoLateral =  gamepad1.left_stick_x;
            double comandoGiro    =  gamepad1.right_stick_x;

            double pFE = comandoFrente + comandoLateral + comandoGiro;
            double pFD = comandoFrente - comandoLateral - comandoGiro;
            double pTE = comandoFrente - comandoLateral + comandoGiro;
            double pTD = comandoFrente + comandoLateral - comandoGiro;

            rodaFD.setPower(pFD);
            rodaFE.setPower(pFE);
            rodaTE.setPower(pTE);
            rodaTD.setPower(pTD);
            //------------------------------------

            //Codigo INTAKE ----------------------
            if (gamepad1.a && !aa){
                op = op + 1;
            }
            if (op %2 == 0){
                intake.setPower(1);
            }
            else {
                intake.setPower(0);
            }
            aa = gamepad1.a;
            //-----------------------------------

            //Codigo TORRETA---------------------
            double pt = gamepad2.left_stick_x;
            int ps  = torreta.getCurrentPosition();

            if (ps >= lmax && pt > 0){
                pt = 0;
            }
            else if (ps <= lmin && pt < 0) {
                pt = 0;
            }

            torreta.setPower(pt);
            //-------------------------------------

            //Codigo Potencia LANCADOR----------
            if (gamepad2.dpad_up && !ad) {
                pw = pw + .1;
            } else if (gamepad2.dpad_down && !rm) {
                pw = pw - .1;
            }

            ad = gamepad2.dpad_up;
            rm = gamepad2.dpad_down;

            if (pw > 1) {
                pw = 1;
            } else if (pw < 0) {
                pw = 0;
            }
            //--------------------------------------

            //Codigo LANCADOR------------------------
            if (gamepad2.right_trigger >= .1) {
                lancador.setPower(pw);
            } else {
                lancador.setPower(0);
            }
            //----------------------------------------

            //TELEMETRIA------------------------------
            telemetry.addData("Potencia: ", pw);
            telemetry.addData("Posicao Torreta: ", ps);
            telemetry.update();
            //----------------------------------------
        }
    }
}