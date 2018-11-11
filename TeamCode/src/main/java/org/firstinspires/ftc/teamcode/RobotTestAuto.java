/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.helper.AutoBot;
import org.firstinspires.ftc.teamcode.helper.Robot;

@Autonomous(name="RobotTestAuto", group="DogeCV")

public class RobotTestAuto extends OpMode
{
    // Detector object
    private GoldAlignDetector detector;
    private Robot bot;
    private double currentServoPos;
    private int counter;

    @Override
    public void init() {
        telemetry.addData("Status", "DogeCV 2018.0 - Gold Align Example");
        bot = new Robot(hardwareMap,telemetry);
        AutoBot aB = new AutoBot(hardwareMap, telemetry);
        aB.setUpMotors();
        currentServoPos = bot.getArmServoPosition();
        counter = 0;

        // Set up detector
        detector = new GoldAlignDetector(); // Create detector
        detector.init(hardwareMap.appContext, CameraViewDisplay.getInstance()); // Initialize it with the app context and camera
        detector.useDefaults(); // Set detector to use default settings

        // Optional tuning
        detector.alignSize = 100; // How wide (in pixels) is the range in which the gold object will be aligned. (Represented by green bars in the preview)
        detector.alignPosOffset = 0; // How far from center frame to offset this alignment zone.
        detector.downscale = 0.4; // How much to downscale the input frames

        detector.areaScoringMethod = DogeCV.AreaScoringMethod.MAX_AREA; // Can also be PERFECT_AREA
        //detector.perfectAreaScorer.perfectArea = 10000; // if using PERFECT_AREA scoring
        detector.maxAreaScorer.weight = 0.005; //

        detector.ratioScorer.weight = 5; //
        detector.ratioScorer.perfectRatio = 1.0; // Ratio adjustment
        detector.enable(); // Start the detector!
    }

    /*
     * Code to run REPEATEDLY when the driver hits INIT
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {

    }

    /*
     * Code to run REPEATEDLY when the driver hits PLAY
     */
    @Override
    public void loop() {
        telemetry.addData("IsAligned", detector.getAligned()); // Is the bot aligned with the gold mineral?
        telemetry.addData("Center X Pos", detector.getXPosition()); // Gold center X position.
        telemetry.addLine("Rect Edge X Pos: " + detector.temp.x); // Gold right side X position
        double cX = detector.getXPosition();
        double sX = detector.temp.x;
        boolean found = detector.isFound();

        if(currentServoPos == 0 && counter == 0){
            //Moving arm when auto starts
            if(bot.armMotor1.getCurrentPosition() < 1600 && counter == 0)
                bot.armMotor1.setPower(-1);
            if(bot.armMotor2.getCurrentPosition() < 1600 && counter == 0)
                bot.armMotor2.setPower(-1);
            bot.armServo.setPosition(1);
            counter++;
            //Moving robot back
            if(counter == 1) {
                if (bot.topLeft.getCurrentPosition() < 2500)
                    bot.topLeft.setPower(0.75);
                if (bot.topRight.getCurrentPosition() < 2500)
                    bot.topRight.setPower(-0.75);
                if (bot.botLeft.getCurrentPosition() < 2500)
                    bot.botLeft.setPower(0.75);
                if (bot.botRight.getCurrentPosition() < 2500)
                    bot.botRight.setPower(-0.75);
            }
            counter++;
            //Rotating robot to find gold piece
            if(counter == 2){
                if(!found){
                    bot.topLeft.setPower(-0.5);
                    bot.topRight.setPower(-0.5);
                    bot.botLeft.setPower(-0.5);
                    bot.botRight.setPower(-0.5);
                }
                else{
                    bot.topLeft.setPower(0.0);
                    bot.topRight.setPower(0.0);
                    bot.botRight.setPower(0.0);
                    bot.botLeft.setPower(0.0);
                }
            }
            counter++;
            //Making the robot strafe to align
            if(counter == 3) {
            /*
                if(cX > 10) {
                    if (sX < 250) {
                        bot.topLeft.setPower(0.75);
                        bot.topRight.setPower(0.75);
                        bot.botRight.setPower(-0.75);
                        bot.botLeft.setPower(-0.75);
                    }
                    else if (sX > 250) {
                        bot.topLeft.setPower(-0.75);
                        bot.topRight.setPower(-0.75);
                        bot.botLeft.setPower(0.75);
                        bot.botRight.setPower(0.75);
                    }
                    else {
                        bot.topLeft.setPower(0.0);
                        bot.topRight.setPower(0.0);
                        bot.botLeft.setPower(0.0);
                        bot.botRight.setPower(0.0);
                    }
                    */
            }
        }
    }

        //After moving mineral dropping team marker in square(code for servo)

        //Code to end off


    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
        // Disable the detector
        detector.disable();
    }

}