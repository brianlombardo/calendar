import 'package:flutter/material.dart';
import 'package:url_launcher/url_launcher.dart';

class ImdbLink extends StatelessWidget {
  final String _imdbUrl;

  const ImdbLink(this._imdbUrl, {super.key});

  @override
  Widget build(BuildContext context) => InkWell(
      child: Container(
        color: Theme.of(context).colorScheme.secondary,
        child: const Padding(
          padding: EdgeInsets.symmetric(horizontal: 64, vertical: 24),
          child: Text('View on IMDB'),
        ),
      ),
      onTap: () {
        launchUrl(Uri.parse(_imdbUrl));
      });
}
