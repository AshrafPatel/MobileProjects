import React,{Component } from 'react';
import { StyleSheet, Text, View, ScrollView } from 'react-native';
import { Audio } from 'expo-av';
import Session from '../myNewProject/components/Session/Session'
import Break from '../myNewProject/components/Break/Break'
import Timer from '../myNewProject/components/Timer/Timer'
import { AppLoading } from 'expo';
import * as Font from 'expo-font';
import { LinearGradient } from 'expo-linear-gradient';
import Constants from 'expo-constants';
import { Dimensions } from 'react-native';


let customFonts = {
  "Righteous": "https://fonts.googleapis.com/css2?family=Righteous&display=swap",
  "Roboto": "https://fonts.googleapis.com/css2?family=Roboto&display=swap"
};


export default class App extends Component {
  constructor() {
    super()
    this.state = {
      howLongWork: 25,
      howLongBreak: 5,
      session: true,
      started: false,
      timer: "25:00",
      workTimer: 1500,
      breakTimer: 300,
      intervalID: "",
      play: "#",
      // newTimerNumber: "This should change",
      fontsLoaded: false
    }
    this.howLongBreakInc = this.howLongBreakInc.bind(this)
    this.howLongBreakDec = this.howLongBreakDec.bind(this)
    this.howLongWorkInc = this.howLongWorkInc.bind(this)
    this.howLongWorkDec = this.howLongWorkDec.bind(this)
    this.reset = this.reset.bind(this)
    this.checkSessionBreak = this.checkSessionBreak.bind(this)
    this.countDown = this.countDown.bind(this)
    this.startTimer = this.startTimer.bind(this)
    this.provideNumber = this.provideNumber.bind(this)
  }

  async _loadFontsAsync() {
    await Font.loadAsync(customFonts);
    this.setState({ fontsLoaded: true });
  }

  componentDidMount() {
    this._loadFontsAsync();
  }
  
  howLongBreakInc() {
    if (this.state.howLongBreak >= 60) {
      this.setState({
        howLongBreak: 60
      });
    } else {
      this.setState((prevState) => {
        return {
          howLongBreak: prevState.howLongBreak + 1,
          breakTimer: (prevState.howLongBreak+1)*60
        }
      });
    }
  }
  
  howLongBreakDec() {
    if (this.state.howLongBreak <= 1) {
      this.setState({
        howLongBreak: 1
      });
    } else {
      this.setState((prevState) =>{
        return {
          howLongBreak: prevState.howLongBreak - 1,
          breakTimer: (prevState.howLongBreak-1)*60
        }
      });
    }
  }

  howLongWorkInc() {
    if (this.state.howLongWork >= 60) {
      this.setState({
        howLongWork: 60
      });
    } else { 
      this.setState((prevState) =>{
        return {
          howLongWork: prevState.howLongWork + 1,
          workTimer: (prevState.howLongWork +1) * 60,
          timer: this.provideNumber(this.state.workTimer)
        }
      })
    }
  }
  
  howLongWorkDec() {
    if (this.state.howLongWork <= 1) {
      this.setState({
        howLongWork: 1,
        timerNumber: 60
      });
    } else {
      this.setState((prevState) =>{
        return {
          howLongWork: prevState.howLongWork - 1,
          workTimer: (prevState.howLongWork-1)*60,
          timer: this.provideNumber(this.state.workTimer)
        }
      })
    }
  }
  
  reset() {
    clearInterval(this.state.intervalID)
    this.setState({
      howLongWork: 25,
      howLongBreak: 5,
      session: true,
      started: false,
      timer: "25:00",
      workTimer: 1500,
      breakTimer: 300,
      play: "#"
    })
  }
  
  checkSessionBreak() {
    if (this.state.workTimer <= 0 && this.state.session) {
      let newTimer = this.state.howLongBreak*60
      this.setState({
        session: false,
        breakTimer: this.state.howLongBreak*60,
        newTimerNumber: newTimer,
        play: "./assets/sounds/BeepSound.wav"
      })
    } else if (this.state.breakTimer <= 0 && !this.state.session) {
      let newTimer = this.state.howLongWork*60
      this.setState({
        session: true,
        workTimer: this.state.howLongWork*60,
        newTimerNumber: newTimer,
        play: "./assets/sounds/BeepSound.wav"
      })
    }
  }
  
  provideNumber(timerNumber) {
    let timerNum = ""
    let second = 0
    let minute = 0
    if (timerNumber >= 60) {
      minute = Math.floor(timerNumber / 60)
      second = (timerNumber % 60) - 1 
    } else {
      second = timerNumber - 1
      minute = 0
    }
    if (second <= 0) {
        second = 0
      }
    timerNum = minute.toString().padStart(2, '0') + ":" +second.toString().padStart(2, '0')
    return timerNum
  }
  
  startTimer() {
    if  (!this.state.started) {
      let timer = setInterval(this.countDown, 1000)
      this.setState({
        intervalID: timer,
        started: true
      })
    } else {
      clearInterval(this.state.intervalID)
      this.setState({
        started: false
      })
    }
  }
  
  countDown() {
    if (this.state.session) {
      this.setState((prevState) => {
        if (prevState.workTimer <= 0) {
          return {
            workTimer: 0,
            timer: this.provideNumber(0),
            play: "./assets/sounds/BeepSound.wav"
          }
        } 
        // else if (prevState.play != "#" && prevState.workTimer < prevState.newTimerNumber-2) {
        //     return {
        //       workTimer: prevState.workTimer - 1,
        //       play: "#"
        //     }
        // } 
        else {
          return {
            workTimer: prevState.workTimer - 1,
            play: "#"
          }
        }
      })
    } else if (!this.state.session) {
      this.setState((prevState) => {
        if (prevState.breakTimer <= 0) {
          return {
            breakTimer: 0,
            timer: this.provideNumber(0),
            play: "./assets/sounds/BeepSound.wav"
          }
        } 
        // else if (prevState.play != "#" && prevState.breakTimer < prevState.newTimerNumber-2) {
        //   return {
        //     workTimer: prevState.breakTimer - 1,
        //     play: "#"
        //   }
        // } 
        else {
          return {
            breakTimer: prevState.breakTimer - 1,
            play: "#"
          }
        }
      })
    }
    this.checkSessionBreak()
  }

  async playSound() {
    const soundObject = new Audio.Sound();
    try {
      const {play} = this.state
      while (play == "./assets/sounds/BeepSound.wav") {
        await soundObject.loadAsync(require("./assets/sounds/BeepSound.wav"));
        await soundObject.playAsync();
      } 
      await soundObject.unloadAsync();
    } catch (error) {
      // An error occurred!
    }
  }

  render() {
    const {howLongBreak, session, started, timer, workTimer, breakTimer, howLongWork, play, newTimerNumber} = this.state;

    this.playSound()

    if (this.state.fontsLoaded) {
      return (
      <View clasName="main-body" style={style.mainBody}>
        <LinearGradient
          colors={['#374FA5', '#5A6AB3']}>
          <Text class="h1" style={style.h1}>Timer</Text>
          <View className="main-app" style={style.mainApp}>
            <Session 
              howLongWorkInc={this.howLongWorkInc}
              howLongWork={howLongWork}
              howLongWorkDec={this.howLongWorkDec}
            />
            <Timer session={session} 
                  started={started}
                  reset={this.reset}
                  workTimer={this.provideNumber(workTimer)}
                  breakTimer={this.provideNumber(breakTimer)}
                  newTimerNumber={newTimerNumber}
                  pausePlay={this.startTimer}
                  play={play}
            />
            <Break 
              howLongBreakInc={this.howLongBreakInc}
              howLongBreak={howLongBreak}
              howLongBreakDec={this.howLongBreakDec}
            />
          </View>
          </LinearGradient>
        </View>
    );
    } else {
      return <AppLoading />;
    }
  }
}

const style = StyleSheet.create({
  h1: {
    textAlign: "center",
    textShadowColor: "white",
    color: "white",
    fontSize: 95,
    fontFamily: "Righteous",
    textShadowOffset: {width: 2, height: 2},
    textTransform: "uppercase",
    textShadowRadius: 3,
    paddingTop: Constants.statusBarHeight,
    fontWeight: "bold"
  },

  mainBody: {
    justifyContent: "space-between"
  },

  mainApp: {
    flexDirection: "row",
    width: "100%",
    justifyContent: "space-evenly",
    width: Dimensions.get('window').width,
	  height: Dimensions.get('window').height-100
  }
});
