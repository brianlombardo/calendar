let playground = """
{"equipment": [
  "Swing",
  { "Slide": "Chute" },
  { "MerryGoRound": { "radius": 5 }},
  { "Sandbox": { "width": 10, "height": 1, "depth": 10 }}
]}
"""
let playgroundResponse = createPlayground(name: playground)
print(playgroundResponse)
