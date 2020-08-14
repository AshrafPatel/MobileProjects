import React,{Component} from 'react';
import { StyleSheet, Text, View, Button, TouchableOpacity, Dimensions } from 'react-native';

export default class Break extends Component {
    constructor(props) {
      super(props);
    }

    //"⮝" "⮟"
    
    render() {
      const {howLongBreak, howLongBreakInc, howLongBreakDec} = this.props
      return (
        <View className="main-break" style={style.mainBreak}>
          <Text id="break-label" style={style.h3}>Break Length</Text>
          <TouchableOpacity onPress={howLongBreakInc}>
            <Text id="break-increment" style={style.timerBtn} title="&#8593;">&#9650;</Text>
          </TouchableOpacity>
          <Text id="break-length" style={style.numbersText}>{howLongBreak}</Text>
          <TouchableOpacity onPress={howLongBreakDec}>
            <Text id="break-decrement" style={style.timerBtn} title="&#8595;">&#9660;</Text>
          </TouchableOpacity>
        </View>
      )
    }
}

const style=StyleSheet.create({
  mainBreak: {
    height:  Dimensions.get('window').height-600,
    alignItems: "center",
    alignSelf: "center",
    justifyContent: "space-evenly",
    alignItems: "center"
  },

  h3: {
    fontFamily: 'Roboto',
    fontSize: 20,
    color: "white",
    textAlign: "center",
    fontWeight: "bold"
  },

  timerBtn: {
    color: "#F95C6D",
    fontSize: 60,
    margin: "auto",
    alignSelf: "flex-end"
  },

  numbersText: {
    color: "white",
    fontSize: 60,
    fontFamily: 'Righteous',
    fontWeight: "900",
    textAlign: "center",
    margin: "auto"
  }
})
