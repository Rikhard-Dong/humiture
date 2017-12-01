
$(document).ready(function() {
  
      var xmlhttp = new XMLHttpRequest();
      xmlhttp.onreadystatechange = function() {
          if (this.readyState == 4 && this.status == 200) {
         
             var  myObj=this.responseText;
      
             var a=eval('('+myObj+')');
            var shuzu=[];
            var  shidu=[];
            var parentNode=[];
             for(var value in a.data.ths)
             {
                var temper=[];
                var nodeId=a.data.ths[value].nodeId;
                var temper=a.data.ths[value].temper;
                var humidity=a.data.ths[value].humidity;
                var resportTime=a.data.ths[value].resportTime;
                var parentNode2=a.data.ths[value].parentNode;
                var json2=JSON.stringify(temper)+",";
                shuzu.push(temper);
                shidu.push(humidity);
                parentNode.push(parentNode2);
             }
             var aaa=eval(shuzu);
          var title = {
            text: '1号网关各个节点月平均历史温度曲线'   
        };
        var subtitle = {
             text: 'time change'
        };
        var xAxis = {
            categories: ['一月', '二月', '三月', '四月', '五月', '六月'
                   ,'七月', '八月', '九月', '十月', '十一月', '十二月']
        }; 
        var yAxis = {
           title: {
              text: 'Temperature (\xB0C)'
           },
           plotLines: [{
              value: 0,
              width: 1,
              color: '#808080'
           }]
        };   
     
        var tooltip = {
           valueSuffix: '\xB0C'
        }
     
        var legend = {
           layout: 'vertical',
           align: 'right',
           verticalAlign: 'middle',
           borderWidth: 0
        };
       
    
  
  
        var series =  [
           {
              name: '一号节点',
              data: aaa
           }, 
           {
              name: '二号节点',
              data: [-0.2, 0.8, 5.7, 11.3, 17.0, 22.0, 24.8,
                 24.1, 20.1, 14.1, 8.6, 2.5]
           }, 
           {
              name: '三号节点',
              data: [-0.9, 0.6, 3.5, 8.4, 13.5, 17.0, 18.6,
                 17.9, 14.3, 9.0, 3.9, 1.0,]
           },
           {
              name: '四号节点',
              data: [3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 
                 16.6, 14.2, 10.3, 6.6, 4.8]
           }
        ];
       
        var json = {};
        json.title = title;
        json.subtitle = subtitle;
        json.xAxis = xAxis;
        json.yAxis = yAxis;
        json.tooltip = tooltip;
        json.legend = legend;
        json.series = series;
        $('#container').highcharts(json);
          }
      };
      xmlhttp.open("GET", "http://192.168.3.2:8888/data/th/ths", true);
      xmlhttp.send();
 });
  $(document).ready(function() {
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
           var  myObj=this.responseText;
           var a=eval('('+myObj+')');
           var  shidu=[];
           for(var value in a.data.ths)
           {
              var temper=[];
              var nodeId=a.data.ths[value].nodeId;
              var temper=a.data.ths[value].temper;
              var humidity=a.data.ths[value].humidity;
              var resportTime=a.data.ths[value].resportTime;
              var json2=JSON.stringify(temper)+",";
          
              shidu.push(humidity);
           }
    var title = {
        text: '1号网关各个节点月平均历史湿度曲线'   
    };
    var subtitle = {
         text: 'time change '
    };
    var xAxis = {
        categories: ['一月', '二月', '三月', '四月', '五月', '六月'
               ,'七月', '八月', '九月', '十月', '十一月', '十二月']
    }; 
    var yAxis = {
       title: {
          text: 'humidity：%RH'
       },
       plotLines: [{
          value: 0,
          width: 1,
          color: '#808080'
       }]
    };   
 
    var tooltip = {
       valueSuffix: '%RH'
    }
 
    var legend = {
       layout: 'vertical',
       align: 'right',
       verticalAlign: 'middle',
       borderWidth: 0
    };
    var  bbb=eval(shidu);
    var series =  [
       {
          name: '一号节点',
          data: bbb
       }, 
       {
          name: '二号节点',
          data: [-0.2, 0.8, 5.7, 11.3, 17.0, 22.0, 24.8,
             24.1, 20.1, 14.1, 8.6, 2.5]
       }, 
       {
          name: '三号节点',
          data: [-0.9, 0.6, 3.5, 8.4, 13.5, 17.0, 18.6,
             17.9, 14.3, 9.0, 3.9, 1.0]
       },
       {
          name: '四号节点',
          data: [3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 
             16.6, 14.2, 10.3, 6.6, 4.8]
       }
    ];
    var json = {};
    json.title = title;
    json.subtitle = subtitle;
    json.xAxis = xAxis;
    json.yAxis = yAxis;
    json.tooltip = tooltip;
    json.legend = legend;
    json.series = series;
    $('#container2').highcharts(json);
}

};
    xmlhttp.open("GET", "http://192.168.3.2:8888/data/th/ths", true);
    xmlhttp.send();
 });

//  $(document).ready(function() {
//     var title = {
//         text: '2号网关各个节点历史温度月变化曲线'   
//     };
//     var subtitle = {
//          text: 'time change '
//     };
//     var xAxis = {
//         categories: ['一月', '二月', '三月', '四月', '五月', '六月'
//                ,'七月', '八月', '九月', '十月', '十一月', '十二月']
//     }; 
//     var yAxis = {
//        title: {
//           text: 'Temperature (\xB0C)'
//        },
//        plotLines: [{
//           value: 0,
//           width: 1,
//           color: '#808080'
//        }]
//     };   
//     var tooltip = {
//        valueSuffix: '\xB0C'
//     }
//     var legend = {
//        layout: 'vertical',
//        align: 'right',
//        verticalAlign: 'middle',
//        borderWidth: 0
//     };
//     var series =  [
//        {
//           name: '一号节点',
//           data: [7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2,
//              26.5, 23.3, 18.3, 13.9, 9.6]
//        }, 
//        {
//           name: '二号节点',
//           data: [-0.2, 0.8, 5.7, 11.3, 17.0, 22.0, 24.8,
//              24.1, 20.1, 14.1, 8.6, 2.5]
//        }, 
//        {
//           name: '三号节点',
//           data: [-0.9, 0.6, 3.5, 8.4, 13.5, 17.0, 18.6,
//              17.9, 14.3, 9.0, 3.9, 1.0]
//        },
//        {
//           name: '四号节点',
//           data: [3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 
//              16.6, 14.2, 10.3, 6.6, 4.8]
//        }
//     ];
//     var json = {};
//     json.title = title;
//     json.subtitle = subtitle;
//     json.xAxis = xAxis;
//     json.yAxis = yAxis;
//     json.tooltip = tooltip;
//     json.legend = legend;
//     json.series = series;
//     $('#container3').highcharts(json);
//  });
//  $(document).ready(function() {
//       var title = {
//           text: '2号网关各个节点历史温度日变化曲线'   
//       };
//       var subtitle = {
//            text: 'time change '
//       };
//       var xAxis = {
//           categories: ['1号', '2号', '3号', '4号', '5号', '6号'
//                  ,'7号', '8号', '9号', '10号', '11号', '12号','13号', '14号', '15号', '16号', '17号', '18号'
//                  ,'19号', '20号', '21号', '22号', '23号', '24号','25号', '26号', '27号', '28号', '29号', '30号'
//                  ]
//       }; 
//       var yAxis = {
//          title: {
//             text: 'Temperature (\xB0C)'
//          },
//          plotLines: [{
//             value: 0,
//             width: 1,
//             color: '#808080'
//          }]
//       };   
//       var tooltip = {
//          valueSuffix: '\xB0C'
//       }
//       var legend = {
//          layout: 'vertical',
//          align: 'right',
//          verticalAlign: 'middle',
//          borderWidth: 0
//       };
//       var series =  [
//          {
//             name: '一号节点',
//             data: [7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2,
//                26.5, 23.3, 18.3, 13.9, 9.6,7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2,
//                26.5, 23.3, 18.3, 13.9, 9.6,7.0, 6.9, 9.5, 14.5, 18.2, 21.5
//               ]
//          }, 
//          {
//             name: '二号节点',
//             data: [7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2,
//                   26.5, 23.3, 18.3, 6.9, 9.6,7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2,
//                   26.5, 23.3, 18.3, 1.9, 9.6,7.0, 6.9, 9.5, 8.5, 18.2, 21.5]
//          }, 
//          {
//             name: '三号节点',
//             data: [7.0, 6.9, 9.5, 2.5, 18.2, 21.5, 25.2,
//                   26.5, 23.3, 18.3, 13.9, 9.6,7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2,
//                   26.5, 23.3, 18.3, 13.9, 1.6,7.0, 6.9, 1.5, 14.5, 18.2, 21.5]
//          },
//          {
//             name: '四号节点',
//             data: [7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2,
//                   26.5, 23.3, 18.3, 13.9, 9.6,7.0, 12.9, 9.5, 14.5, 18.2, 21.5, 25.2,
//                   26.5, 23.3, 18.3, 13.9, 9.6,7.0, 6.9, 9.5, 2.5, 18.2, 21.5]
//          }
//       ];
//       var json = {};
//       json.title = title;
//       json.subtitle = subtitle;
//       json.xAxis = xAxis;
//       json.yAxis = yAxis;
//       json.tooltip = tooltip;
//       json.legend = legend;
//       json.series = series;
//       $('#container4').highcharts(json);
//    });
   